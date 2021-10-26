package com.example.peluqueria_app.Utils;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.peluqueria_app.ui.views.HomeActivity;

public class Acelerometro {

    private SensorManager accelerometerSensorManager;
    private Sensor sensorA;
    private SensorEventListener sensorListener;
    public static final int SHAKE_LIMIT = 800;
    public static int contShake = 0;
    private final HomeActivity homeActivity;

    public Acelerometro(HomeActivity homeActivity){
        this.homeActivity=homeActivity;
    }

    public void setShake(){
        sensorA = accelerometerSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(sensorA == null){
            return;
        }
        sensorListener = new SensorEventListener() {
            long ultimaAct = 0;
            float last_x;
            float last_y;
            float last_z;
            @Override
            public void onSensorChanged(SensorEvent event) {
                    long tiempoActual = System.currentTimeMillis();

                    if((tiempoActual - ultimaAct) > 100){
                        long diffTime = (tiempoActual - ultimaAct);
                        ultimaAct = tiempoActual;
                        float x = event.values[0];
                        float y = event.values[1];
                        float z = event.values[2];
                        float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;
                        if(speed > SHAKE_LIMIT){
                                System.out.println("Velocidad: " + speed);
                                contShake++;
                             }
                        last_x = x;
                        last_y = y;
                        last_z = z;
                        if(contShake == 3){
                            homeActivity.abrirWhatsapp();
                            contShake = 0;
                        }
                    }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                System.out.println("Sensor aceletrometro calibrado");
            }
        };
        start();
    }
    public void start(){
        accelerometerSensorManager.registerListener(sensorListener,sensorA,SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop(){
        accelerometerSensorManager.unregisterListener(sensorListener, accelerometerSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    public void setSensorManager(SensorManager accelerometer){
        this.accelerometerSensorManager = accelerometer;
    }
}
