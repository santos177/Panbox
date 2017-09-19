package com.example.root.panbox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by NgocTri on 10/22/2016.
 */

public class ListViewAdapter extends ArrayAdapter<Product> {
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
        ImageView img = (ImageView) v.findViewById(R.id.imageView);
        TextView txtTitle = (TextView) v.findViewById(R.id.txtTitle);
       // TextView txtFolio = (TextView) v.findViewById(R.id.txtFolio);
        TextView txtDirection = (TextView) v.findViewById(R.id.txtDirection);
        TextView txtDate = (TextView) v.findViewById(R.id.txtDate);
        Integer img_id = Integer.valueOf(product.getImageId());
        img.setImageResource(R.drawable.entrega);


        txtTitle.setText(product.getTitle());
       // txtFolio.setText("Documento n° ".concat(product.getDocument_n()));
        txtDirection.setText(product.getDirection());
        txtDate.setText(product.getDate_p());
        return v;
    }
}
