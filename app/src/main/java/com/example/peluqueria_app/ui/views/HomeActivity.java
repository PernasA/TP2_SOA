package com.example.peluqueria_app.ui.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.peluqueria_app.R;
import com.example.peluqueria_app.presenters.HomePresenter;

import java.net.URLEncoder;

public class HomeActivity extends AppCompatActivity {

    AlertDialog alertDialog;
     @SuppressLint("UseSwitchCompatOrMaterialCode")
     Switch switchLuz;
     @SuppressLint("UseSwitchCompatOrMaterialCode")
     Switch switchPantalla;

HomePresenter homePresenter;
    @RequiresApi(api = Build.VERSION_CODES.M)
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

        homePresenter = new HomePresenter(this);
        homePresenter.inicializarCronometro();

homePresenter.inicializarSensores((SensorManager) getSystemService(SENSOR_SERVICE),this);
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
        homePresenter.registrarListener();
    }

    @Override
    protected void onPause() {
        Log.i("Ejecuto", "Ejecuto OnPause");
        super.onPause();
        homePresenter.sacarListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Ejecuto", "Ejecuto OnStop");
        homePresenter.sacarListener();
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
        homePresenter.sacarListener();
    }

    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener botonesListeners = v -> {
        switch (v.getId()) {
            case R.id.imageButton:
                alertDialog = new AlertDialog.Builder(HomeActivity.this).setTitle("Información del sensor")
                        .setMessage("Utilizamos un sensor para que tu celular se adapte a la luz que hay en el ambiente. Sabemos que en la barbería pueden haber zonas con distinta luminosidad.")
                        .setPositiveButton("Ok, entiendo", (dialog, which) -> alertDialog.hide()).show();
                break;
            case R.id.imageButton2:
                alertDialog = new AlertDialog.Builder(HomeActivity.this).setTitle("Información del sensor")
                        .setMessage("Utilizamos un sensor para que puedas agitar el celular y de esa forma abras un chat de Whatsapp con nuestros barberos y puedas hacer tu consulta.")
                        .setPositiveButton("Ok, entiendo", (dialog, which) -> alertDialog.hide()).show();
                break;
            case R.id.switch1:
                if(switchLuz.isChecked()){
                    Toast.makeText(getApplicationContext(), "Activaste el sensor de luz", Toast.LENGTH_LONG).show();
                    homePresenter.activarSensorLuz();
                }else{
                    Toast.makeText(getApplicationContext(), "Desactivaste el sensor de luz", Toast.LENGTH_LONG).show();
                    homePresenter.pararSensorLuz();
                }
                break;
            case R.id.switch2:
                if(switchPantalla.isChecked()){
                    Toast.makeText(getApplicationContext(), "Activaste el switch de aceleracion", Toast.LENGTH_LONG).show();
                    homePresenter.activarSensorAcelerometro();
                }else{
                    Toast.makeText(getApplicationContext(), "Desactivaste el sensor de aceleracion", Toast.LENGTH_LONG).show();
                    homePresenter.pararSensorAcelerometro();
                }
                break;
            default:
                Toast.makeText(getApplicationContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
        }
    };


    @SuppressLint("QueryPermissionsNeeded")
    public void abrirWhatsapp() {
        try{
            PackageManager packageManager = this.getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone="+ 549+1138155179 +"&text=" + URLEncoder.encode("Buenos días barbero. Le escribo desde la aplicación para  ", "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }else {
                Toast.makeText(getApplicationContext(), "Error al abrir Whatsapp", Toast.LENGTH_LONG).show();
            }
        } catch(Exception e) {
            Log.e("ERROR WHATSAPP",e.toString());
            Toast.makeText(getApplicationContext(), "Error al abrir Whatsapp", Toast.LENGTH_LONG).show();
        }
    }


}