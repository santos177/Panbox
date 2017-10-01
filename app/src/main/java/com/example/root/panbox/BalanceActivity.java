package com.example.root.panbox;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BalanceActivity extends AppCompatActivity {
     EditText kilogramos, unidades,total_saldo_anterior,total_dinero,total_saldo;
    final Context context1 = this;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_activity);
        getWindow().setBackgroundDrawableResource(R.drawable.gradient);
        Button button2 = (Button) findViewById(R.id.generar_pdf);
        kilogramos = (EditText) findViewById(R.id.kgs);
        unidades = (EditText) findViewById(R.id.unidades_p);
        total_saldo_anterior = (EditText) findViewById(R.id.total_saldo_anterior);
        total_dinero = (EditText) findViewById(R.id.total_dinero);
        total_saldo= (EditText) findViewById(R.id.total_saldo);
        GetBalance();
        GetBread();
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context1);
                alertDialogBuilder.setMessage("Es posible que se modifiquen las columnas de Saldos. ¿Desea Continuar?");
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                GeneratePDF();
                                UpdateSaldo();
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




            }
        });


    }

    public void GeneratePDF() {
        // TODO Auto-generated method stub
        DateFormat dateFormat = new SimpleDateFormat("MMM d,EEE,''yyyy");
        Date date = new Date();
        String fecha = date.toString();
        String filename = "Panbox"+fecha;
        String filecontent = "Contenido";
        SimpleTable fop = new SimpleTable();
        String[] data1 = GetClientData();
        String[] balance = GetBalance();
        String[] bread = GetBread() ;
        if (fop.write(filename, filecontent, data1,balance,bread)) {
            Toast.makeText(getApplicationContext(),
                    filename + ".pdf Creado", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(getApplicationContext(), "I/O error", Toast.LENGTH_SHORT).show();
        }
    }


    public String[] GetClientData() {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("SELECT * FROM clientes", null);
        String[] data = new String[6*fila.getCount()];
        //Cursor fila = bd.rawQuery("select nombre_cliente, precio_unitario, total_pan, saldo_anterior, total, saldo from clientes where nombre_cliente='" + nombre_cliente + "'", null);
        if (fila.moveToFirst()) {
            for (int j=0;j < fila.getCount();j++) {

                for (int i = 0; i < 6; i++) {
                    data[i + 6*j] = fila.getString(i);
                }
                fila.moveToNext();

            }


            return data;
        } else {


            Toast.makeText(this, "No existe ningún cliente con ese nombre", Toast.LENGTH_SHORT).show();
        }
        bd.close();
     return null;
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), GridActivity.class);  //Instanciamos un intent, que es llamar a GridLayout
        //finish();
        startActivity(intent);

    }
    public String[] GetBalance() {  // genera balances simples del saldo anterior total, total de dinero del día y saldo total del día.
        String suma_s;
        int suma=0;
        String[] resultado = new String[3];
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String[] column = {"saldo_anterior", "total", "saldo"};
        for (int i=0; i < column.length;i++) {
            Cursor fila = bd.rawQuery("select " + column[i] + " from clientes ", null);
            if (fila.moveToFirst()) {
                for (int j = 0; j < fila.getCount(); j++) {
                    String sum = fila.getString(0);
                    if (sum != null) {
                        suma = suma + Integer.parseInt(sum);
                    }
                    fila.moveToNext();
                }
                suma_s = String.valueOf(suma);
                if (i==0) {
                    total_saldo_anterior.setText(suma_s);
                    resultado[0] = suma_s;
                }else if (i==1){
                    total_dinero.setText(suma_s);
                    resultado[1] = suma_s;
                }else if(i==2){
                    total_saldo.setText(suma_s);
                    resultado[0] = suma_s;
                }
                 suma = 0;
            } else {


                Toast.makeText(this, "No existe ningún cliente con ese nombre",

                        Toast.LENGTH_SHORT).show();
            }
        }
        bd.close();
        return resultado;
    }


    public String[] GetBread() {  // suma las cantidades de kilogramos y unidades de pan diario.
        String suma_s;
        String [] bread = new String[2];
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        int[] column = {0,1};
        for (int i=0; i < column.length;i++) {
            float suma=0;   // la suma se setea en cada loop
            Cursor fila = bd.rawQuery("select total_pan from clientes where tipo='" + column[i] + "'", null);
            if (fila.moveToFirst()) {
                for (int j = 0; j < fila.getCount(); j++) {
                    suma = suma + fila.getFloat(0);
                    fila.moveToNext();
                }
                suma_s = String.valueOf(suma);
                if (i==0) { // se suman las unidades de pan
                    if(!suma_s.equals("null")) {
                        unidades.setText(suma_s);
                        bread[0] = suma_s;
                    }
                }else if (i==1){  // se suman los kilogramos de pan
                    if(!suma_s.equals("null")) {
                        kilogramos.setText(suma_s);
                        bread[1] = suma_s;
                    }
                }
            } else {


                Toast.makeText(this, "No existe ningún cliente con ese nombre", Toast.LENGTH_SHORT).show();
            }
        }
        bd.close();
        return bread;
    }


public void   UpdateSaldo(){   // traspasamos la información del saldo de hoy, al de ayer (preparando la app para el día siguiente)

    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getApplicationContext(), "administracion", null, 1);
    SQLiteDatabase bd = admin.getWritableDatabase();

    bd.execSQL("UPDATE clientes SET saldo_anterior = saldo");
    bd.execSQL("UPDATE clientes SET saldo = 0");
    bd.close();




}



}






