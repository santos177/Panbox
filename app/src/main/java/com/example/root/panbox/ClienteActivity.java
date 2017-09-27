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


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente_activity);
        button = (Button) findViewById(R.id.button_siguiente1); //botón de iniciar sesión
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

                    Toast.makeText(getApplicationContext(), "precio unitario * total pan ="+print, Toast.LENGTH_SHORT).show();
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
                                    Manifest.permission.CAMERA,
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

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,

                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();


        Cursor fila = bd.rawQuery("select precio_unitario, total_pan, saldo_anterior, total, saldo from clientes where nombre_cliente='"+nombre_cliente+"'", null);

        if (fila.moveToFirst()) {
            //Toast.makeText(getApplicationContext(),"posicion columna 2:"+fila.getString(0),Toast.LENGTH_SHORT).show();
             precio_unitario.setText(fila.getString(0));
             total_pan.setText(fila.getString(1));
            saldo_anterior.setText(fila.getString(2));
            total.setText(fila.getString(3));
            saldo.setText(fila.getString(4));

        } else

            Toast.makeText(this, "No existe ningún cliente con ese nombre",

                    Toast.LENGTH_SHORT).show();

        bd.close();

    }

    public void DeleteClient(String cliente){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getApplicationContext(), "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        bd.execSQL("delete from clientes WHERE nombre_cliente='"+ cliente +"'");
        bd.close();


    }



}
