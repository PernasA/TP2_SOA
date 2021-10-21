package com.example.peluqueria_app.ui.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.peluqueria_app.R;

public class LoginActivity extends Activity {

    private EditText usernameEditText;
    private EditText passwordEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText= findViewById(R.id.username);
        passwordEditText= findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.botonLogin);
        loginButton.setOnClickListener(botonesListeners);
        final Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(botonesListeners);
        Log.i("Ejecuto","Ejecuto onCreate");

    }
    @Override
    protected void onStart()
    {
        Log.i("Ejecuto","Ejecuto Onstart");
        super.onStart();
    }

    @Override
    protected void onResume()
    {
        Log.i("Ejecuto","Ejecuto OnResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("Ejecuto","Ejecuto OnPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Ejecuto","Ejecuto OnStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i("Ejecuto","Ejecuto OnRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Ejecuto","Ejecuto OnDestroy");
    }

    //Metodo que actua como Listener de los eventos que ocurren en los componentes graficos de la activty
    private final View.OnClickListener botonesListeners = v -> {
        Intent intent;

        //Se determina que componente genero un evento
        switch (v.getId()) {
            //Si se ocurrio un evento en el boton OK
            case R.id.botonLogin:
                Toast.makeText(getApplicationContext(), "Presionó el botón de login", Toast.LENGTH_LONG).show();
                //se genera un Intent para poder lanzar la activity principal
                //intent=new Intent(MainActivity.this,DialogActivity.class);
                //Se le agrega al intent los parametros que se le quieren pasar a la activyt principal
                //cuando se lanzado
                //intent.putExtra("textoOrigen",txtOrigen.getText().toString());
                //se inicia la activity principal
                //startActivity(intent);
                break;
            case R.id.registerButton:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(getApplicationContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
        }

    };
}