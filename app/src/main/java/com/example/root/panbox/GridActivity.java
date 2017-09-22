package com.example.root.panbox;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class GridActivity extends AppCompatActivity {

    private ViewStub stubList;
    private ListView listView;
    private ListViewAdapter listViewAdapter;
    List<Product> productList = new ArrayList<>();
    final Context context = this;
    private Integer buffer = 0;
    private Integer vacio = 0;
    String Cliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_main);

        setTitle("Clientes");

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
                //Aquí recargar stublist

                return true;
            case R.id.opcion_2:

                return true;
            case R.id.opcion_3:

                return true;
            case R.id.opcion_4:

                return true;
            case R.id.desconectar_grid:
                Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);  //Instanciamos un intent, que es llamar a GridLayout
                finish();
                startActivity(intent2);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class ListServicesRequest extends AsyncTask<Void, Void, Void> {
        private final String USER_AGENT = "Mozilla/5.0";
        private String json_decoded = "";

        @Override
        protected Void doInBackground(Void... params) {

             String [] Clientes= {"Palomo","Libertad","Alicia","Pedro","Juan"};
            for (int i = 0; i < Clientes.length; i++) {
                Product temp = new Product("1", "2", "3", "4", "5", "6", Clientes[i], "8", "9", "10", "11", "12", "13");
                productList.add(temp);
                //initDatabase();
            }
            setConfigGrid();
        return null;
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
                    Cliente = productList.get(i).getDate_p();
                    intent.putExtra("Cliente",Cliente);
                    startActivity(intent);

                }
            });
        }

    }

    @Override
    public void onBackPressed() {

    }









}







