package com.example.root.panbox;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class BalanceActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        Button button2 = (Button) findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GeneratePDF();

            }
        });


    }

    public void GeneratePDF() {
        // TODO Auto-generated method stub
        String filename = "Panbox";
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
        String[] data = new String[30];
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,

                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery("SELECT * FROM clientes", null);
        //Cursor fila = bd.rawQuery("select nombre_cliente, precio_unitario, total_pan, saldo_anterior, total, saldo from clientes where nombre_cliente='" + nombre_cliente + "'", null);
        if (fila.moveToFirst()) {
            for (int j=0;j < 5;j++) {

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


}




