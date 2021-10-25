package com.example.peluqueria_app.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.example.peluqueria_app.APIs.RetrofitAPI;
import com.example.peluqueria_app.models.APIResponse;
import com.example.peluqueria_app.ui.views.FinishActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cronometro implements Runnable {

    Handler handler;
    Context context;
    public Cronometro(Handler h, Context context) {
        this.handler = h;
        this.context =context;
    }

    @Override

    public void run() {
        try {
            Thread.sleep(900000); //900000 milisegundos = 15 minutos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Runnable alerta = () -> new AlertDialog.Builder(context)
                .setTitle("Sesión expirada")
                .setMessage("Para continuar en la aplicación, por favor refresque su token de acceso")
                .setPositiveButton("Refrescar", (dialog, which) -> {
                    HashMap<String, String> hm = new HashMap<>();
                    hm.put("Content-Type", "application/json");
                    hm.put("Authorization", "Bearer " + SessionInfo.refreshToken);
                    actualizarTokenApi(hm);
                    Runnable r = new Cronometro(handler, context);
                    Thread t = new Thread(r);
                    t.start();
                })
                .setNegativeButton("Salir", (dialog, which) -> {
                    Intent intent = new Intent(context, FinishActivity.class);
                    //Clear all activities and start new task
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                })
                .show();
        handler.post(alerta);
    }
    private void actualizarTokenApi(Map<String, String> headers) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://so-unlam.net.ar/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI rfApi = retrofit.create(RetrofitAPI.class);
        Call<APIResponse> call = rfApi.putRefreshToken(headers);
        call.enqueue(new Callback<APIResponse>() {

            @Override
            public void onResponse(@NonNull Call<APIResponse> call, @NonNull Response<APIResponse> response) {
                if(response.code() == 200) {
                    assert response.body() != null;
                    SessionInfo.authToken = response.body().getToken();
                }
            }

            @Override
            public void onFailure(@NonNull Call<APIResponse> call, @NonNull Throwable t) {}

        });
    }
}
