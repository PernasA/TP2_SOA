package com.example.peluqueria_app.presenters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.NonNull;

import com.example.peluqueria_app.Utils.SessionInfo;
import com.example.peluqueria_app.models.APIResponse;
import com.example.peluqueria_app.models.EventRequest;
import com.example.peluqueria_app.models.EventResponse;
import com.example.peluqueria_app.models.LoginRequest;
import com.example.peluqueria_app.models.MySharedPreferences;
import com.example.peluqueria_app.models.MySingletonRetrofit;
import com.example.peluqueria_app.ui.views.LoginActivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        LoginRequest lr = new LoginRequest(mail, password);
        Call<APIResponse> call = MySingletonRetrofit.getRetrofitApi().postLogin(lr);
        call.enqueue(new Callback<APIResponse>() {

            @Override
            public void onResponse(@NonNull Call<APIResponse> call, @NonNull Response<APIResponse> response) {
                int[] cantidades= {0,0};
                if(response.code() == 200) {
                    mensajeRespuesta = "Login exitoso";

                    MySharedPreferences.registrarLogin(context,mail,activity,cantidades);
                    APIResponse ar = response.body();
                    assert ar != null;
                    SessionInfo.authToken = ar.getToken();
                    SessionInfo.refreshToken = ar.getToken_refresh();
                    registrarLoginApi();

                } else {
                    mensajeRespuesta = "Usuario y/o contrase??a incorrectos";
                    activity.response(mensajeRespuesta,-1,-1);
                }
                activity.response(mensajeRespuesta,cantidades[0],cantidades[1]);
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
        EventRequest er = new EventRequest("Login", "Se ha registrado un logueo a la aplicacion.");
        Call<EventResponse> call = MySingletonRetrofit.getRetrofitApi().postEvent(headers, er);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventResponse> call, @NonNull Response<EventResponse> response) { }

            @Override
            public void onFailure(@NonNull Call<EventResponse> call, @NonNull Throwable t) { }
        });
    }

}
