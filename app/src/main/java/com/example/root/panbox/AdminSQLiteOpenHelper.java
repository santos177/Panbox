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

        //aquí creamos la tabla de usuario ( nombre_cliente, precio unitario, total pan,saldo anterior,total,saldo)
        db.execSQL("create table clientes (nombre_cliente text, precio_unitario integer, total_pan integer, saldo_anterior integer,total integer,saldo integer)");
        String [] Clientes= {"Palomo","Libertad","Alicia","Pedro","Juan"};
       /* for (int i = 0; i < Clientes.length; i++) {
            db.execSQL("insert into clientes (nombre_cliente) VALUES ('"+ Clientes[i] +"')");
        }*/

        db.execSQL("insert into clientes nombre_cliente values ('"+ Clientes[0] +"')");
    }


  @Override
    public void onUpgrade(SQLiteDatabase db, int version1, int version2) {

        db.execSQL("drop table if exists clientes");

        db.execSQL("create table clientes(id integer primary key, precio_unitario integer, total_pan float, saldo_anterior integer,saldo integer)");

    }

}
