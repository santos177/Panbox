package com.example.root.panbox;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity {
    private ViewStub stubList;
    private ListView listView;
    private ListViewAdapter listViewAdapter;
    List<Product> productList = new ArrayList<>();
    final Context context = this;
    Context context1 = this;
    String Cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_main);

        setTitle("Clientes");
        setConfigGrid();
        //get list of product
        ListServicesRequest loadProfileRequest = new ListServicesRequest();
        loadProfileRequest.execute();

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");

            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            // presumably, not relevant
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.grid_activity_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        SharedPreferences preferences = getSharedPreferences("mypreferences", MODE_PRIVATE);
        String usuario = preferences.getString("correo", "");
        switch (item.getItemId()) {
            case R.id.agregar_cliente:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                final EditText nombre_e = new EditText(GridActivity.this);
                alertDialogBuilder.setTitle("Agrega El Nuevo Cliente");
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                nombre_e.setLayoutParams(lp);
                alertDialogBuilder.setView(nombre_e);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Continuar",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                String cliente = nombre_e.getText().toString();
                                if (!nombre_e.getText().toString().equals("")) {
                                    AddClient(cliente);
                                    Intent intent2 = new Intent(getApplicationContext(), GridActivity.class);  //Instanciamos un intent, que es llamar a GridLayout
                                    startActivity(intent2);
                                }else {
                                      Toast.makeText(context1,"Error: Debe ingresar el nombre del Cliente",Toast.LENGTH_LONG).show();
                                }


                            }
                        })
                        .setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
            case R.id.balance:
                Intent intent3 = new Intent(getApplicationContext(), BalanceActivity.class);  //Instanciamos un intent, que es llamar a GridLayout
                finish();
                startActivity(intent3);
                return true;

            case R.id.desconectar_grid:
                Intent intent4 = new Intent(getApplicationContext(), LoginActivity.class);  //Instanciamos un intent, que es llamar a GridLayout
                finish();
                startActivity(intent4);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class ListServicesRequest extends AsyncTask<Void, Void, Void> {
        private final String USER_AGENT = "Mozilla/5.0";
        private String json_decoded = "";

        @Override
        protected Void doInBackground(Void... params) {
            String[] Clientes = GetClientList();
            for (int i = 0; i < Clientes.length; i++) {
                Product temp = new Product(Clientes[i]);
                productList.add(temp);
            }
            return null;
        }

    }
        private void setConfigGrid() {
            stubList = (ViewStub) findViewById(R.id.stub_list);
            //Inflate ViewStub before get view
            stubList.inflate();
            listView = (ListView) findViewById(R.id.mylistview);
            stubList.setVisibility(View.VISIBLE);
            listViewAdapter = new ListViewAdapter(getApplicationContext(), R.layout.list_item, productList);
            listView.setAdapter(listViewAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), ClienteActivity.class);  //Instanciamos un intent, que es llamar a GridLayout
                    finish();
                    Cliente = productList.get(i).getCliente();
                    intent.putExtra("Cliente", Cliente);
                    startActivity(intent);

                }
            });
        }


    @Override
    public void onBackPressed() {

    }


    public String[] GetClientList() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,

                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select nombre_cliente from clientes", null);
        fila.getCount();
        String[] data = new String[fila.getCount()];
        if (fila.moveToFirst()) {
            for (int j=0;j < fila.getCount();j++) {
                data[j] = fila.getString(0);
                fila.moveToNext();
            }


            return data;
        }
        bd.close();
        return null;
    }

    public void AddClient(String cliente){
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getApplicationContext(), "administracion", null, 1);
        int tipo_default = 0;
        SQLiteDatabase bd = admin.getWritableDatabase();
        bd.execSQL("insert into clientes (nombre_cliente, precio_unitario, saldo_anterior,total,saldo,tipo) VALUES ('"+ cliente +"','"+ tipo_default +"','"+ tipo_default +"','"+ tipo_default +"','"+ tipo_default +"','"+ tipo_default +"')");
        bd.close();


     }

}







