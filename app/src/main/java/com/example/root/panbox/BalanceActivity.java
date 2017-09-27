package com.example.root.panbox;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_activity);
        getWindow().setBackgroundDrawableResource(R.drawable.gradient);
        Button button2 = (Button) findViewById(R.id.generar_pdf);
        kilogramos = (EditText) findViewById(R.id.kgs);
        unidades = (EditText) findViewById(R.id.unidades);
        total_saldo_anterior = (EditText) findViewById(R.id.total_saldo_anterior);
        total_dinero = (EditText) findViewById(R.id.total_dinero);
        total_saldo= (EditText) findViewById(R.id.total_saldo);
        GetBalance();
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GeneratePDF();

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
        if (fop.write(filename, filecontent, data1)) {
            Toast.makeText(getApplicationContext(),
                    filename + ".pdf Creado", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(getApplicationContext(), "I/O error",
                    Toast.LENGTH_SHORT).show();
        }
    }


    public String[] GetClientData() {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,

                "administracion", null, 1);
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


            Toast.makeText(this, "No existe ningún cliente con ese nombre",

                    Toast.LENGTH_SHORT).show();
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
    public void GetBalance() {
        String suma_s;
        int suma=0;
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,

                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String[] column = {"saldo_anterior", "total", "saldo"};
        for (int i=0; i < column.length;i++) {
            Cursor fila = bd.rawQuery("select " + column[i] + " from clientes ", null);
            if (fila.moveToFirst()) {
                for (int j = 0; j < 5; j++) {
                    String sum = fila.getString(0);
                    if (sum != null) {
                        suma = suma + Integer.parseInt(sum);
                    }
                    fila.moveToNext();
                }
                suma_s = String.valueOf(suma);
                if (i==0) {
                    total_saldo_anterior.setText(suma_s);
                }else if (i==1){
                    total_dinero.setText(suma_s);
                }else if(i==2){
                    total_saldo.setText(suma_s);
                }
                 suma = 0;
            } else {


                Toast.makeText(this, "No existe ningún cliente con ese nombre",

                        Toast.LENGTH_SHORT).show();
            }
        }
        bd.close();
    }
}






