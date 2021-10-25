package com.example.peluqueria_app.presenters;

import android.content.Context;
import android.os.Handler;

import com.example.peluqueria_app.Utils.Cronometro;

public class HomePresenter {
    Context context;
    public HomePresenter(Context context){
        this.context = context;
    }

    public void inicializarCronometro() {
        Handler handler = new Handler();
        Runnable cronometro = new Cronometro(handler, context);
        Thread t = new Thread(cronometro);
        t.start();
    }

}

