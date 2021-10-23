package com.example.peluqueria_app.ui.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.peluqueria_app.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
    }

    @Override
    protected void onPause() {
        Log.i("Ejecuto", "Ejecuto OnPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Ejecuto", "Ejecuto OnStop");
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
    }

    private final View.OnClickListener botonesListeners = v -> {
        switch (v.getId()) {
            //Si se ocurrio un evento en el boton OK
            case R.id.botonLogin:
        }
    };
}