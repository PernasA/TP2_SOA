package com.example.peluqueria_app.ui.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.BatteryManager;
import com.example.peluqueria_app.R;
import com.example.peluqueria_app.presenters.LoginMailPresenter;

public class LoginMailActivity extends Activity {

        LoginMailPresenter presenter = new LoginMailPresenter(this);
        private EditText mailEditText;
        private EditText codigoEditText;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login_mail);

            mailEditText= findViewById(R.id.editTextMail);
            codigoEditText = findViewById(R.id.editTextConfirmationCode);

            final Button enviarMailButton = findViewById(R.id.buttonEnviarMail);
            enviarMailButton.setOnClickListener(botonesListeners);

            final Button confirmButton = findViewById(R.id.buttonConfirmar);
            confirmButton.setOnClickListener(botonesListeners);

            Log.i("Ejecuto","Ejecuto onCreate");
        }

        @Override
        protected void onStart()
        {
            Log.i("Ejecuto","Ejecuto Onstart");
            super.onStart();
            // Call battery manager service
            BatteryManager bm = (BatteryManager) this.getSystemService(BATTERY_SERVICE);
            // Get the battery percentage and store it in a INT variable
            int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            Toast.makeText(this,"Su batería es del: "+batLevel+"%",Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onResume()
        {
            Log.i("Ejecuto","Ejecuto OnResume");
            super.onResume();
        }

        @Override
        protected void onPause() {
            Log.i("Ejecuto","Ejecuto OnPause");
            super.onPause();
        }

        @Override
        protected void onStop() {
            super.onStop();
            Log.i("Ejecuto","Ejecuto OnStop");
        }

        @Override
        protected void onRestart() {
            super.onRestart();

            Log.i("Ejecuto","Ejecuto OnRestart");
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            Log.i("Ejecuto","Ejecuto OnDestroy");
        }

        //Metodo que actua como Listener de los eventos que ocurren en los componentes graficos de la activty
        private final View.OnClickListener botonesListeners = v -> {
            if (v.getId() == R.id.buttonEnviarMail) {
        if(presenter.hayConeccion(getApplicationContext())){
                if (presenter.verificarCampos(mailEditText.getText().toString()) == null) {
                    Toast.makeText(getApplicationContext(), "Enviamos un código de verificación al correo que ingresaste", Toast.LENGTH_SHORT).show();
                    presenter.sendEmail(mailEditText.getText().toString());
                } else
                    Toast.makeText(getApplicationContext(), presenter.verificarCampos(mailEditText.getText().toString()), Toast.LENGTH_SHORT).show();
            }else{
             new AlertDialog.Builder(LoginMailActivity.this).setTitle("Error de conexión")
                    .setMessage("Verifique conexión a internet y vuelva a iniciar la aplicación")
                    .setPositiveButton("Salir", (dialog, which) -> {
                        finish();
                        //LoginMailActivity.returnInstance().finish();
                    }).show();
        }
            } else if (v.getId() == R.id.buttonConfirmar) {
                if(presenter.codigoValido(codigoEditText.getText().toString())) {
                    Intent intent = new Intent(com.example.peluqueria_app.ui.views.LoginMailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else Toast.makeText(getApplicationContext(), "Código de verificación incorrecto" , Toast.LENGTH_SHORT).show();
            } else Toast.makeText(getApplicationContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
        };
}