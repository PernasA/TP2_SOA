package com.example.peluqueria_app.ui.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.peluqueria_app.R;
import com.example.peluqueria_app.presenters.RegisterPresenter;

import java.util.HashMap;


public class RegisterActivity extends Activity {

    RegisterPresenter presenter = new RegisterPresenter(this);
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText nombreEditText;
    private EditText apellidoEditText;
    private EditText dniEditText;
    final HashMap<Integer, String> listaErrores = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText= findViewById(R.id.editTextMail);
        passwordEditText= findViewById(R.id.editTextPassword);
        nombreEditText= findViewById(R.id.editTextNombre);
        apellidoEditText= findViewById(R.id.editTextApellido);
        dniEditText= findViewById(R.id.editTextDni);
        Button confirmButton = findViewById(R.id.buttonConfirmar);
        confirmButton.setOnClickListener(botonesListeners);
        cargarHashmap(listaErrores);
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
    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener botonesListeners = v -> {
        //Intent intent;
        //Se determina que componente genero un evento
        //se genera un Intent para poder lanzar la activity principal
        //intent=new Intent(MainActivity.this,DialogActivity.class);
        //Se le agrega al intent los parametros que se le quieren pasar a la activyt principal
        //cuando se lanzado
        //intent.putExtra("textoOrigen",txtOrigen.getText().toString());
        //se inicia la activity principal
        //startActivity(intent);
        if (v.getId() == R.id.buttonConfirmar) {
            int result = presenter.verificarCampos(usernameEditText.getText().toString(), passwordEditText.getText().toString(), nombreEditText.getText().toString(), apellidoEditText.getText().toString(), dniEditText.getText().toString());
            if (result == 0) {
                Toast.makeText(getApplicationContext(), "Datos cargados correctamente", Toast.LENGTH_SHORT).show();
                int codigo = presenter.sendEmail(usernameEditText.getText().toString());
                Intent intent = new Intent(RegisterActivity.this, ConfirmCodeActivity.class);
                intent.putExtra("datosRegistro", usernameEditText.getText().toString()+" " +passwordEditText.getText().toString()+ " "+ nombreEditText.getText().toString()+ " " +apellidoEditText.getText().toString()+" "+dniEditText.getText().toString()+" "+codigo);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), listaErrores.get(result), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
        }
    };


    private void cargarHashmap(HashMap<Integer, String> listaCampos) {
        listaCampos.put(991, "Por favor rellene el campo de mail");
        listaCampos.put(992, "Por favor rellene el campo de contraseña");
        listaCampos.put(993, "Por favor rellene el campo de nombre");
        listaCampos.put(994, "Por favor rellene el campo de apellido");
        listaCampos.put(995, "Por favor rellene el campo de DNI");
        listaCampos.put(881, "La contraseña debe ser de más de 7 caracteres");
        listaCampos.put(882, "El mail es inválido");
        listaCampos.put(883, "El DNI es inválido");
        listaCampos.put(884, "No se admiten espacios en ningun campo");
    }


}

