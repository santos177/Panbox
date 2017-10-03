package com.example.root.panbox;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static android.os.Build.VERSION_CODES.M;
import static com.example.root.panbox.R.id.button;

import android.widget.LinearLayout;
import android.widget.Toast;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase.CursorFactory;




public class ClienteActivity extends AppCompatActivity {
    final Context context1 = this;
    private static final int MY_REQUEST_CODE = 1;
    private ImageView ivImage;
    Context context;
    String mCurrentPhotoPath,nombre_cliente;
    EditText precio_unitario,saldo_anterior,saldo,total, total_pan;
    Button button;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cliente_activity_settings, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.eliminar_cliente:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context1);
                alertDialogBuilder.setTitle("¿Seguro que desea eliminar el Cliente?");
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                DeleteClient(nombre_cliente);
                                Intent intent2 = new Intent(getApplicationContext(), GridActivity.class);  //Instanciamos un intent, que es llamar a GridLayout
                                startActivity(intent2);

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
            case R.id.medida:
                AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(
                        context1);
                alertDialog1.setTitle("Seleccione medida");
                alertDialog1
                        .setCancelable(false)
                        .setPositiveButton("Unidades",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Toast.makeText(getApplicationContext(),"Ahora Ud. vende al cliente en Unidades",Toast.LENGTH_LONG).show();
                                MeasureClient(nombre_cliente,0);
                            }
                        })
                        .setNegativeButton("Kgs",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Toast.makeText(getApplicationContext(),"Ahora Ud. vende al cliente en Kilogramos",Toast.LENGTH_LONG).show();
                                MeasureClient(nombre_cliente,1);
                            }
                        });
                // create alert dialog
                AlertDialog alert = alertDialog1.create();
                alert.show();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente_activity);
        button = (Button) findViewById(R.id.button_siguiente1); //botón para guardar la información en la BD
        Bundle extras = getIntent().getExtras();
        precio_unitario = (EditText) findViewById(R.id.n_documento);
        total_pan = (EditText) findViewById(R.id.tipo);
        total_pan.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        saldo_anterior = (EditText) findViewById(R.id.fecha_hora);
        total = (EditText) findViewById(R.id.hora);
        saldo= (EditText) findViewById(R.id.e_saldo);
        nombre_cliente = extras.getString("Cliente");
        Consulta();
        //Allowing Strict mode policy for Nougat support
        //StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        //StrictMode.setVmPolicy(builder.build());
        getWindow().setBackgroundDrawableResource(R.drawable.gradient);
        setTitle(nombre_cliente);
       // setTitle("Nombre del Cliente");
        //Quitar multilinea

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String pu = precio_unitario.getText().toString();
                String tp = total_pan.getText().toString();
                String sa = saldo_anterior.getText().toString();
                String tl = total.getText().toString();
                String so = saldo.getText().toString();

                if ((!pu.equals("")) && (!tp.equals("")) && (!sa.equals("")) && (!tl.equals(""))) {

                    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getApplicationContext(), "administracion", null, 1);

                    SQLiteDatabase bd = admin.getWritableDatabase();


                    // se generan los cálculos : saldo anterior+(precio_unitario*total_pan)-total = saldo
                    int precio_unitario_int = Integer.parseInt(pu);
                    float total_pan_float = Float.parseFloat(tp);
                    int saldo_anterior_int = Integer.parseInt(sa);
                    int total_int = Integer.parseInt(tl);
                    float print = (precio_unitario_int*total_pan_float);
                    int saldo = (int) (saldo_anterior_int+(precio_unitario_int*total_pan_float) - total_int);
                    // convertimos el saldo a String:
                    String saldo_string = String.valueOf(saldo);

                   // Toast.makeText(getApplicationContext(), "precio unitario * total pan ="+print, Toast.LENGTH_SHORT).show();
                    ContentValues registro = new ContentValues();

                    registro.put("precio_unitario", pu);
                    registro.put("total_pan", tp);
                    registro.put("saldo_anterior", sa);
                    registro.put("total", tl);
                    //registro.put("saldo",so);
                    registro.put("saldo", saldo_string);
                    // los inserto en la base de datos
                    int cant = bd.update("clientes", registro, "nombre_cliente='" + nombre_cliente + "'", null);

                    bd.close();

                    if (cant == 1) {

                        Toast.makeText(getApplicationContext(), "Datos modificados con éxito", Toast.LENGTH_SHORT).show();
                        Consulta();
                    } else {
                        Toast.makeText(getApplicationContext(), "No existe usuario", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Faltan datos a ingresar", Toast.LENGTH_SHORT).show();
                }
            }
        });




        if (Build.VERSION.SDK_INT >= M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                if (Build.VERSION.SDK_INT >= M) {
                    requestPermissions(new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_REQUEST_CODE);
                }
            }
        }

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), GridActivity.class);  //Instanciamos un intent, que es llamar a GridLayout
        //finish();
        startActivity(intent);

    }


    public void Consulta() {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();


        Cursor fila = bd.rawQuery("SELECT precio_unitario, total_pan, saldo_anterior, total, saldo, tipo FROM clientes WHERE nombre_cliente='"+nombre_cliente+"'", null);

        if (fila.moveToFirst()) {
            //Toast.makeText(getApplicationContext(),"posicion columna 2:"+fila.getString(0),Toast.LENGTH_SHORT).show();
             precio_unitario.setText(fila.getString(0));
              String measure="";
             if(fila.getInt(5) == 1){
                 measure = "kilogramos";
             }else if (fila.getInt(5) == 0) {
                 measure = "unidades";
             }
            total_pan.setHint(measure);
            total_pan.setText(fila.getString(1));
            saldo_anterior.setText(fila.getString(2));
            total.setText(fila.getString(3));
            saldo.setText(fila.getString(4));

        } else

            Toast.makeText(this, "No existe ningún cliente con ese nombre", Toast.LENGTH_SHORT).show();

        bd.close();

    }

    public void DeleteClient(String cliente){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getApplicationContext(), "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        bd.execSQL("DELETE FROM clientes WHERE nombre_cliente='"+ cliente +"'");
        bd.close();


    }

    public void MeasureClient(String cliente,int tipo){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getApplicationContext(), "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("tipo", tipo);
        String measure="";
        if(tipo == 1){
            measure = "kilogramos";
        }else if (tipo == 0) {
            measure = "unidades";
        }
        total_pan.setHint(measure);
        // los inserto en la base de datos
        int cant = bd.update("clientes", registro, "nombre_cliente='" + cliente + "'", null);

        bd.close();

    }

}
