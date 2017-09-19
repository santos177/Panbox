package com.example.root.panbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class LoginActivity extends AppCompatActivity {

    private EditText m1, m2;   //declaración de los elementos que están en el xml
    Button button;
    String correo,contrasena;
    String iniciar="iniciando";
    public static final String SERVER_URL_REQUEST_LOGIN = "http://appdispatcher.ctac.cl/Dispatcher/getData.php";

    public void setPreferences (String var1) {  //Método para recordar que el usuario ya ha realizado un login
        SharedPreferences preferences = getSharedPreferences("mypreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("correo", var1);
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getWindow().setBackgroundDrawableResource(R.drawable.gradient);



        //-------------- SE VERIFICAN LOS PREFERENCES PARA VER SI EL USUARIO YA HA HECHO LOGIN O NO--------------------------

        /*SharedPreferences preferences = getSharedPreferences("mypreferences", MODE_PRIVATE);
        contrasena = preferences.getString("contrasena","");
        if(!contrasena.matches("")){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            finish();
            startActivity(intent);

        }*/

        //-------- INICIO LOGIN Y BASE DE DATOS -------

        button = (Button) findViewById(R.id.button); //botón de iniciar sesión
        m1= (EditText) findViewById(R.id.editText);  //igualación entre elementos del xml y del java
        m2= (EditText) findViewById(R.id.editText3);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {          //cuando se activa el botón de iniciar sesión capturamos los datos de correo y contraseña
                correo = m1.getText().toString();    //Luego los transformamos en Strings
                contrasena = m2.getText().toString();

                if((correo.equals("1234") && (contrasena.equals("1234")))){
                    //Se resetea el campo de error
                    m1.setError(null);
                    m2.setError(null);
                    setPreferences(correo);  // guardamos en memoria que el usuario ha realizado un login
                    Intent intent = new Intent(getApplicationContext(), GridActivity.class);  //Instanciamos un intent, que es llamar a GridLayout
                    finish();
                    startActivity(intent);
                }else{
                    if(TextUtils.isEmpty(correo)){
                        m1.setError("Campo requerido");
                    }
                    else
                    if(TextUtils.isEmpty(contrasena)){
                        m2.setError("Campo requerido");
                    }
                    else{
                        m1.setError("Los datos ingresados son incorrectos");
                        m2.setError("Los datos ingresados son incorrectos");
                    }

                }





            }
        });

        //---------------------------------------

    }

    //-------- PARTE EXTRA LOGIN FACEBOOK Y TWITTER -------





}
