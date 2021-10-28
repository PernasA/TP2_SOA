package com.example.peluqueria_app.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.BatteryManager;

import com.example.peluqueria_app.ui.views.ConfirmCodeActivity;
import com.example.peluqueria_app.ui.views.LoginActivity;

public class MySharedPreferences {

    public static void registrarLogin(Context context, String mail, LoginActivity activity,int[] cantidades){

        android.content.SharedPreferences sharedPreferences = context.getSharedPreferences("registroSharedPreferences", Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(mail, sharedPreferences.getInt(mail, 0) + 1);

        BatteryManager bm = (BatteryManager) activity.getSystemService(LoginActivity.BATTERY_SERVICE);
        int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        if(batLevel < 50)editor.putInt(mail+"1", sharedPreferences.getInt(mail+"1", 0) + 1);

        cantidades[0] = sharedPreferences.getInt(mail,0);
        cantidades[1] = sharedPreferences.getInt(mail+"1",0);

        editor.apply();
    }

    public static void registrarRegistroInicial(Context context, String mail, ConfirmCodeActivity activity){
            SharedPreferences sharedPreferences = context.getSharedPreferences("registroSharedPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(mail, 1);

            BatteryManager bm = (BatteryManager) activity.getSystemService(ConfirmCodeActivity.BATTERY_SERVICE);
            int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            int valorInicialBateria;
            if (batLevel < 50) valorInicialBateria = 1;
            else valorInicialBateria = 0;
            editor.putInt(mail + "1", valorInicialBateria);
            editor.apply();
        }

}
