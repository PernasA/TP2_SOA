package com.example.peluqueria_app.presenters;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;

import com.example.peluqueria_app.Utils.SessionInfo;

import java.util.HashMap;

public class CronometroPresenter implements Runnable {

    Handler handler;
    Context context;
    public CronometroPresenter(Handler h, Context context) {
        this.handler = h;
        this.context =context;
    }

    @Override

    public void run() {
        try {
            Thread.sleep(420000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Runnable alerta = () -> new AlertDialog.Builder(context)
                .setTitle("Sesion expirada")
                .setMessage("Para continuar navegando en la aplicacion es necesario refrescar el token")
                .setPositiveButton("Refrescar", (dialog, which) -> {
                    HashMap<String, String> hm = new HashMap<>();
                    hm.put("Content-Type", "application/json");
                    hm.put("Authorization", "Bearer " + SessionInfo.refreshToken);
                   // putData(hm);
                    Runnable r = new CronometroPresenter(handler, context);
                    Thread t = new Thread(r);
                    t.start();
                })
                .setNegativeButton("Salir", (dialog, which) -> {
//TODO: TENGO QUE CERRAR LA APP EN ESTE PUNTO

                })
                .show();
        handler.post(alerta);
    }
}
