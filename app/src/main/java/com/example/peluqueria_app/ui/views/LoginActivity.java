package com.example.peluqueria_app.ui.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.peluqueria_app.R;
import com.example.peluqueria_app.Utils.ControladorConeccion;
import com.example.peluqueria_app.presenters.LoginPresenter;

import java.util.HashMap;

public class LoginActivity extends Activity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;
    final HashMap<Integer, String> listaErrores = new HashMap<>();
    private LoginPresenter presenter = new LoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText= findViewById(R.id.username);
        passwordEditText= findViewById(R.id.password);
         loginButton = findViewById(R.id.botonLogin);
        loginButton.setOnClickListener(botonesListeners);
         registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(botonesListeners);
        cargarHashmap(listaErrores);
        presenter = new LoginPresenter(this);

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
                int result = presenter.verificarCampos(usernameEditText.getText().toString(),passwordEditText.getText().toString());
                if(result == 0) {
                    if (ControladorConeccion.checkConnection(getApplicationContext())) {
                        registerButton.setEnabled(false);
                        loginButton.setEnabled(false);
                        presenter.buscarUsuario(this);
                    } else {
                        new AlertDialog.Builder(LoginActivity.this).setTitle("Error de conexión")
                                .setMessage("Verifique conexión a internet y vuelva a iniciar la aplicación")
                                .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .show();
                    }
                }else Toast.makeText(getApplicationContext(), listaErrores.get(result), Toast.LENGTH_SHORT).show();
                break;
            case R.id.registerButton:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(getApplicationContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
        }

    };

    public void response(String mensajeRespuesta,int cant) {
        Toast.makeText(getApplicationContext(), mensajeRespuesta, Toast.LENGTH_SHORT).show();
        registerButton.setEnabled(true);
        loginButton.setEnabled(true);
        if(mensajeRespuesta.equals("Login exitoso")) {
            new AlertDialog.Builder(LoginActivity.this).setTitle("Registro de actividad")
                    .setMessage("Usted se ha logueado " + cant + " veces en la aplicacion")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent sig = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(sig);
                            finish();
                        }
                    })
                    .show();
        }else{
            Toast.makeText(getApplicationContext(), "Vuelva a ingresar los datos por favor", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarHashmap(HashMap<Integer, String> listaCampos) {
        listaCampos.put(991, "Por favor rellene el campo de mail");
        listaCampos.put(992, "Por favor rellene el campo de contraseña");
        listaCampos.put(881, "La contraseña debe ser de más de 7 caracteres");
        listaCampos.put(882, "El mail es inválido");
        listaCampos.put(884, "No se admiten espacios en ningun campo");
    }
}