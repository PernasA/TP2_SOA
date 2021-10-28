package com.example.peluqueria_app.Utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.provider.Settings;

import com.example.peluqueria_app.ui.views.HomeActivity;

public class LightSensor {
    Context context;
    private SensorManager lightSensorManager;
    private Sensor sensor;
    private SensorEventListener sensorListener;
    private final HomeActivity homeActivity;

    public LightSensor(HomeActivity homeActivity){
        this.homeActivity=homeActivity;
    }
    public void start(){
        lightSensorManager.registerListener(sensorListener,sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop(){
        lightSensorManager.unregisterListener(sensorListener, lightSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
    }

    public void setSensorManager(SensorManager sensorManager, Context context){
        this.lightSensorManager = sensorManager;
        this.context = context;
    }
    public void setReaction(){
        sensor = lightSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(sensor == null){
            return;
        }
        sensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.System.canWrite(context)) {
                        if (event.values[0] < 20) {
                            android.provider.Settings.System.putInt(homeActivity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 255);
                        } else if (event.values[0] < 50) {
                            android.provider.Settings.System.putInt(homeActivity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 127);
                        } else if (event.values[0] < 100) {
                            android.provider.Settings.System.putInt(homeActivity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 63);
                        } else {
                            android.provider.Settings.System.putInt(homeActivity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 15);
                        }

                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                System.out.println("Sensor de luz calibrado");
            }
        };
        start();
    }
}
