package com.example.peluqueria_app.presenters;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

import com.example.peluqueria_app.Utils.Acelerometro;
import com.example.peluqueria_app.Utils.Cronometro;
import com.example.peluqueria_app.ui.views.HomeActivity;

public class HomePresenter {
    Context context;
    Sensor sensor;
    SensorEventListener sensorEventListener;
    SensorManager sensorManager;
    HomeActivity homeActivity;
    Acelerometro acelerometro;

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
        acelerometro = new Acelerometro(homeActivity);
         this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        acelerometro.setSensorManager(this.sensorManager);
        acelerometro.setShake();
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        verificarPermisosBrillo(context);

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event.values[0] < 20) {
                   android.provider.Settings.System.putInt(homeActivity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 255);
                }else if(event.values[0] < 50){
                    android.provider.Settings.System.putInt(homeActivity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 127);
                }else if(event.values[0] < 100){
                    android.provider.Settings.System.putInt(homeActivity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 63);
                }else{
                    android.provider.Settings.System.putInt(homeActivity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 15);
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                System.out.println("Sensor de luz calibrado");
            }
        };
    }

    public void sacarListener() {
        sensorManager.unregisterListener(sensorEventListener);
        acelerometro.stop();
    }

    public void registrarListener(){
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        acelerometro.start();
    }

    public void pararSensorLuz(){
        sensorManager.unregisterListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
    }

    public void pararSensorAcelerometro(){
        acelerometro.stop();
    }

    public void activarSensorLuz(){
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),SensorManager.SENSOR_DELAY_NORMAL);
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

