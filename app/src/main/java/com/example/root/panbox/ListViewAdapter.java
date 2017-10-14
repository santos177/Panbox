package com.example.root.panbox;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by NgocTri on 10/22/2016.
 */

public class ListViewAdapter extends ArrayAdapter<Product> {
    ImageView img;
    public ListViewAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(null == v) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
        }
        Product product = getItem(position);
        img = (ImageView) v.findViewById(R.id.imageView);
        TextView txtTitle = (TextView) v.findViewById(R.id.txtTitle);
       // TextView txtFolio = (TextView) v.findViewById(R.id.txtFolio);
        TextView txtDirection = (TextView) v.findViewById(R.id.txtDirection);
        //TextView txtDate = (TextView) v.findViewById(R.id.txtDate);
        txtTitle.setText(product.getCliente());
        ConsultaImagen(product.getCliente());
        //txtFolio.setText("Documento n° ".concat(product.getDocument_n()));
        if (product.getCobro().equals("0")){
            txtDirection.setText("");
        } else if (product.getCobro().equals("1")) {
            txtDirection.setText("Cobrado");
        }

        // txtDate.setText(product.getCliente());
        return v;
    }


    public void ConsultaImagen(String cliente) {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();


        Cursor fila = bd.rawQuery("SELECT tipo FROM clientes WHERE nombre_cliente='"+cliente+"'", null);

        if (fila.moveToFirst()) {
              if(fila.getInt(0) == 0){
                  img.setImageResource(R.drawable.entrega);  // cliente que trabaja con unidades.

        } else if (fila.getInt(0) == 1){
                  img.setImageResource(R.drawable.retiro);  // cliente que trabaja con kilogramos.
              }
        }

        bd.close();

    }













}
