package com.example.peluqueria_app.presenters;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

import com.example.peluqueria_app.Utils.Acelerometro;
import com.example.peluqueria_app.Utils.Cronometro;
import com.example.peluqueria_app.Utils.LightSensor;
import com.example.peluqueria_app.ui.views.HomeActivity;

public class HomePresenter {
    Context context;
    Sensor sensor;
    SensorEventListener sensorEventListener;
    SensorManager sensorManager;
    HomeActivity homeActivity;
    Acelerometro acelerometro;
    LightSensor lightSensor;

    public HomePresenter(Context context) {
        this.context = context;
    }

    public void inicializarCronometro() {
        Handler handler = new Handler();
        Runnable cronometro = new Cronometro(handler, context);
        Thread t = new Thread(cronometro);
        t.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void inicializarSensores(SensorManager sensorManager, HomeActivity homeActivity) {
        this.homeActivity = homeActivity;

        verificarPermisosBrillo(context);
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        acelerometro = new Acelerometro(homeActivity);
        acelerometro.setSensorManager(this.sensorManager);
        acelerometro.setShake();

        lightSensor = new LightSensor(homeActivity);
        lightSensor.setSensorManager(this.sensorManager,context);
        lightSensor.setReaction();
    }

    public void sacarListener() {
        acelerometro.stop();
        lightSensor.stop();
    }

    public void registrarListener(){
        acelerometro.start();
        lightSensor.start();
    }

    public void pararSensorLuz(){
        lightSensor.stop();
    }

    public void pararSensorAcelerometro(){
        acelerometro.stop();
    }

    public void activarSensorLuz(){
        lightSensor.start();
    }

    public void activarSensorAcelerometro(){
        acelerometro.start();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void verificarPermisosBrillo(Context context){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if(Settings.System.canWrite(context)){
                System.out.println("Se puede modificar el brillo");
            }else{
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                homeActivity.startActivity(intent);
            }
        }
    }
}

