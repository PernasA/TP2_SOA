package com.example.peluqueria_app.ui.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.peluqueria_app.R;
import com.example.peluqueria_app.presenters.ConfirmCodePresenter;

public class ConfirmCodeActivity extends AppCompatActivity {

    ConfirmCodePresenter presenter = new ConfirmCodePresenter(this);
    private EditText codeEditText;
    AlertDialog alertDialog;
    Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_code);
        codeEditText= findViewById(R.id.editTextConfirmationCode);

        confirmButton = findViewById(R.id.buttonConfirmar);
        confirmButton.setOnClickListener(botonesListeners);
        Log.i("Ejecuto","Ejecuto onCreate");

        //se crea un objeto Bundle para poder recibir los parametros enviados por la activity Inicio
        //al momento de ejecutar startActivity
        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
         presenter.guardarDatosRegistro(extras.getString("datosRegistro"));

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

    private final View.OnClickListener botonesListeners = v -> {
        if (v.getId() == R.id.buttonConfirmar) {
            confirmButton.setEnabled(false);
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            if(presenter.hayConeccion(getApplicationContext())) {
                if(presenter.verificarCodigo(codeEditText.getText().toString())){
                    presenter.subirInformacion(this);
                }else Toast.makeText(getApplicationContext(), "Código de verificación incorrecto" , Toast.LENGTH_SHORT).show();
            }else {
                        alertDialog = new AlertDialog.Builder(ConfirmCodeActivity.this).setTitle("Error de conexión")
                        .setMessage("Verifique la conexión a internet y vuelva a iniciar la aplicación")
                        .setPositiveButton("Salir", (dialog, which) -> {
                            finish();
                            //getActivity().finish();
                        }).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
        }
    };

    public void response(String mensajeRespuesta) {
        Toast.makeText(getApplicationContext(), mensajeRespuesta, Toast.LENGTH_SHORT).show();
        confirmButton.setEnabled(true);
        if(mensajeRespuesta.equals("Registro completado")) {
            alertDialog = new AlertDialog.Builder(ConfirmCodeActivity.this)
                    .setTitle("Registro de actividades")
                    .setMessage("Este es su primer logueo en la aplicacion")
                    .setPositiveButton("OK", (dialog, which) -> {
                        //LoginActivity.returnInstance().finish();
                        Intent intent = new Intent(ConfirmCodeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .show();
        }else{
            Toast.makeText(getApplicationContext(), "Vuelva a ingresar los datos por favor", Toast.LENGTH_SHORT).show();
        }
    }
}