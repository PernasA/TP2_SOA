package com.example.peluqueria_app.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.NonNull;

import com.example.peluqueria_app.APIs.RetrofitAPI;
import com.example.peluqueria_app.Utils.SessionInfo;
import com.example.peluqueria_app.models.APIResponse;
import com.example.peluqueria_app.models.EventRequest;
import com.example.peluqueria_app.models.EventResponse;
import com.example.peluqueria_app.models.LoginRequest;
import com.example.peluqueria_app.ui.views.ConfirmCodeActivity;
import com.example.peluqueria_app.ui.views.LoginActivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginPresenter {
    LoginActivity activity;
    String mail, password;
    String mensajeRespuesta = "";
    public LoginPresenter(LoginActivity activity){
        this.activity = activity;
    }

    public int verificarCampos(String mail, String password) {
        this.mail = mail;
        this.password = password;
        if(mail == null || mail.isEmpty())return 991;
        if(password == null || password.isEmpty())return 992;
        if(password.length()< 8) return 881;
        if(isNotValidEmail(mail))return 882;
        if(mail.contains(" ")|| password.contains(" ")) return 884;
        return 0;
    }

    public boolean isNotValidEmail(CharSequence target) {
        return !(!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void buscarUsuario(Context context) {
        Retrofit rf = new Retrofit.Builder()
                .baseUrl("http://so-unlam.net.ar/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI rfApi = rf.create(RetrofitAPI.class);
        LoginRequest lr = new LoginRequest(mail, password);
        Call<APIResponse> call = rfApi.postLogin(lr);
        call.enqueue(new Callback<APIResponse>() {

            @Override
            public void onResponse(@NonNull Call<APIResponse> call, @NonNull Response<APIResponse> response) {
                int cant,cantBateria;
                if(response.code() == 200) {
                    mensajeRespuesta = "Login exitoso";
                    SharedPreferences sharedPreferences = context.getSharedPreferences("registroSharedPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(mail, sharedPreferences.getInt(mail, 0) + 1);

                    BatteryManager bm = (BatteryManager) activity.getSystemService(ConfirmCodeActivity.BATTERY_SERVICE);
                    int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
                    if(batLevel < 50)editor.putInt(mail+"1", sharedPreferences.getInt(mail+"1", 0) + 1);

                    cant = sharedPreferences.getInt(mail,0);
                    cantBateria = sharedPreferences.getInt(mail+"1",0);

                    editor.apply();
                    APIResponse ar = response.body();
                    assert ar != null;
                    SessionInfo.authToken = ar.getToken();
                    SessionInfo.refreshToken = ar.getToken_refresh();
                    registrarLoginApi();
                } else {
                    mensajeRespuesta = "Usuario y/o contraseña incorrectos";
                    cant = -1;
                    cantBateria=-1;
                }
                activity.response(mensajeRespuesta,cant,cantBateria);
            }

            @Override
            public void onFailure(@NonNull Call<APIResponse> call, @NonNull Throwable t) {
                mensajeRespuesta = "Error en el login. Intente en otro momento";
                activity.response(mensajeRespuesta,-1,-1);
            }
        });
    }

    private void registrarLoginApi() { //Luego que lo encuentro guardo esa informacion en el server.
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + SessionInfo.authToken);
        Retrofit rf = new Retrofit.Builder()
                .baseUrl("http://so-unlam.net.ar/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI rfApi = rf.create(RetrofitAPI.class);
        EventRequest er = new EventRequest("Login", "Se ha registrado un logueo a la aplicacion.");
        Call<EventResponse> call = rfApi.postEvent(headers, er);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, @NonNull Response<EventResponse> response) { }

            @Override
            public void onFailure(@NonNull Call<EventResponse> call, @NonNull Throwable t) { }
        });
    }

}
