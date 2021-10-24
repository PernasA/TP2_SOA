package com.example.peluqueria_app.ui.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.peluqueria_app.R;
import com.example.peluqueria_app.presenters.CronometroPresenter;

public class HomeActivity extends AppCompatActivity {

    AlertDialog alertDialog;
     @SuppressLint("UseSwitchCompatOrMaterialCode")
     Switch switchLuz;
     @SuppressLint("UseSwitchCompatOrMaterialCode")
     Switch switchPantalla;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final ImageButton enviarMailButton = findViewById(R.id.imageButton);
        enviarMailButton.setOnClickListener(botonesListeners);

        final ImageButton confirmButton = findViewById(R.id.imageButton2);
        confirmButton.setOnClickListener(botonesListeners);

        switchLuz = findViewById(R.id.switch1);
        switchLuz.setOnClickListener(botonesListeners);

         switchPantalla = findViewById(R.id.switch2);
        switchPantalla.setOnClickListener(botonesListeners);

        handler = new Handler();
        Runnable cronometro = new CronometroPresenter(this.handler, this);
        Thread t = new Thread(cronometro);
        t.start();
    }

    @Override
    protected void onStart() {
        Log.i("Ejecuto", "Ejecuto Onstart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i("Ejecuto", "Ejecuto OnResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("Ejecuto", "Ejecuto OnPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Ejecuto", "Ejecuto OnStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i("Ejecuto", "Ejecuto OnRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Ejecuto", "Ejecuto OnDestroy");
    }

    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener botonesListeners = v -> {
        switch (v.getId()) {
            //Si se ocurrio un evento en el boton OK
            case R.id.botonLogin:
                break;
            case R.id.imageButton:
                alertDialog = new AlertDialog.Builder(HomeActivity.this).setTitle("Información del sensor")
                        .setMessage("Utilizamos un sensor para que tu celular se adapte a la luz que hay en el ambiente. Sabemos que en la barbería pueden haber zonas con distinta luminosidad")
                        .setPositiveButton("Ok, entiendo", (dialog, which) -> alertDialog.hide()).show();
                break;
            case R.id.imageButton2:
                alertDialog = new AlertDialog.Builder(HomeActivity.this).setTitle("Información del sensor")
                        .setMessage("Utilizamos un sensor para que puedas guardar el celular en el bolsillo y no quede prendido. Si tenés que guardar rápido el celular o estás apurado esta herramienta es una excelente opción")
                        .setPositiveButton("Ok, entiendo", (dialog, which) -> alertDialog.hide()).show();
                break;
            case R.id.switch1:
                if(switchLuz.isChecked()){
                    Toast.makeText(getApplicationContext(), "Activaste el switch 1", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Desactivaste el switch 1", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.switch2:
                if(switchPantalla.isChecked()){
                    Toast.makeText(getApplicationContext(), "Activaste el switch 2", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Desactivaste el switch 2", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                Toast.makeText(getApplicationContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
        }

    };



}