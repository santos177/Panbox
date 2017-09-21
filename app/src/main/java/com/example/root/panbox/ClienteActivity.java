package com.example.root.panbox;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static android.os.Build.VERSION_CODES.M;
import android.widget.Toast;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase.CursorFactory;




public class ClienteActivity extends AppCompatActivity implements View.OnClickListener{

    Button photoButton,siguiente;
    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_PIC_REQUEST = 1111;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int MY_REQUEST_CODE = 1;
    private ImageView ivImage;
    private static final String IMAGE_CAPTURE_FOLDER = "/uploads";
    private static File file;
    private static Uri _imagefileUri;
    private static String _bytes64Sting, _imageFileName;
    public static String URL = "http://appdispatcher.ctac.cl/Dispatcher/uploadImage.php";
    Context context;
    String mCurrentPhotoPath,nombre_cliente;
    EditText precio_unitario,saldo_anterior,saldo,total, total_pan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        precio_unitario = (EditText) findViewById(R.id.n_documento);
        total_pan = (EditText) findViewById(R.id.tipo);
        saldo_anterior = (EditText) findViewById(R.id.tipo);
        total = (EditText) findViewById(R.id.fecha_hora);
        saldo= (EditText) findViewById(R.id.e_saldo);
        nombre_cliente = extras.getString("Cliente");
        Consulta();
        //Allowing Strict mode policy for Nougat support
        //StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        //StrictMode.setVmPolicy(builder.build());
        setContentView(R.layout.cliente_activity);
        getWindow().setBackgroundDrawableResource(R.drawable.gradient);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        setTitle(nombre_cliente);
       // setTitle("Nombre del Cliente");
        //Quitar multilinea

        if (Build.VERSION.SDK_INT >= M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                if (Build.VERSION.SDK_INT >= M) {
                    requestPermissions(new String[]{
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_REQUEST_CODE);
                }
            }
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_siguiente1:


                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), GridActivity.class);  //Instanciamos un intent, que es llamar a GridLayout
        finish();
        startActivity(intent);

    }


    public void Consulta() {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,

                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();


        Cursor fila = bd.rawQuery("select precio_unitario from clientes where nombre_cliente='"+nombre_cliente+"'", null);

        if (fila.moveToFirst()) {

            precio_unitario.setText(fila.getString(1));
            /*total_pan.setText(fila.getString(2));
            saldo_anterior.setText(fila.getString(3));
            total.setText(fila.getString(4));
            saldo.setText(fila.getString(5));*/

        } else

            Toast.makeText(this, "No existe ningún cliente con ese nombre",

                    Toast.LENGTH_SHORT).show();

        bd.close();

    }





}
