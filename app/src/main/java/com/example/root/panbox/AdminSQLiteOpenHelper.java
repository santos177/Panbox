package com.example.root.panbox;

/**
 * Created by root on 20-09-17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, nombre, factory, version);

    }

    @Override

    public void onCreate(SQLiteDatabase db) {

        //aquí creamos la tabla de usuario ( nombre_cliente, precio unitario, total pan,saldo anterior,total,saldo,tipo)  tipo: "0" = unidades, "1" = kilos
        db.execSQL("create table clientes ( nombre_cliente text, precio_unitario integer, total_pan float, saldo_anterior integer, total integer, saldo integer, tipo integer, cobro integer)");
        String [] Clientes= {"Palomo","Libertad","Alicia","Pedro","Juan","Pepe Tapia","Rafael Garay","Sam Sepiol"};
        int tipo_default = 0;
        for (int i = 0; i < Clientes.length; i++) {
            db.execSQL("insert into clientes (nombre_cliente,precio_unitario, saldo_anterior,total,saldo,tipo,cobro) VALUES ('"+ Clientes[i] +"','"+ tipo_default +"','"+ tipo_default +"','"+ tipo_default +"','"+ tipo_default +"','"+ tipo_default +"','"+ tipo_default +"')");
        }

       // db.execSQL("insert into clientes (nombre_cliente,precio_unitario) values ('"+Clientes[0]+"','"+precio_unitario+"')");
    }


  @Override
    public void onUpgrade(SQLiteDatabase db, int version1, int version2) {

        db.execSQL("drop table if exists clientes");

        db.execSQL("create table clientes(nombre_cliente text, precio_unitario integer, total_pan float, saldo_anterior integer,saldo integer)");

    }

}
