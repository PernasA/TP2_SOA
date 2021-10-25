package com.example.peluqueria_app.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ControladorConeccion {
    private static ConnectivityManager connectivityManager;
    private static NetworkInfo networkInfo;

    public static boolean checkConnection(Context c) {
        connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
