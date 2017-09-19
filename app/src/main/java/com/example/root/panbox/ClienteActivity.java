package com.example.root.panbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static android.os.Build.VERSION_CODES.M;

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
    String mCurrentPhotoPath;
    EditText precio_unitario,saldo_anterior,saldo,total, total_pan;

    //Datos cliente
    String direccion_c, nombre_c, telefono_c, hora_c, descripcion_c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();


        precio_unitario = (EditText) findViewById(R.id.n_documento);
        total_pan = (EditText) findViewById(R.id.tipo);
        saldo_anterior = (EditText) findViewById(R.id.tipo);
        total = (EditText) findViewById(R.id.fecha_hora);
        saldo= (EditText) findViewById(R.id.e_saldo);

        //Allowing Strict mode policy for Nougat support
        //StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        //StrictMode.setVmPolicy(builder.build());
        setContentView(R.layout.cliente_activity);
        getWindow().setBackgroundDrawableResource(R.drawable.gradient);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        setTitle(extras.getString("Cliente"));
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




}
