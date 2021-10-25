package com.example.peluqueria_app.presenters;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.peluqueria_app.APIs.RetrofitAPI;
import com.example.peluqueria_app.Utils.ControladorConeccion;
import com.example.peluqueria_app.Utils.SessionInfo;
import com.example.peluqueria_app.models.APIResponse;
import com.example.peluqueria_app.models.RegistroRequest;
import com.example.peluqueria_app.ui.views.ConfirmCodeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfirmCodePresenter {
    final ConfirmCodeActivity activity;
    String nombre,apellido ,mail,password,dni,code;
    String mensajeRespuesta= "";
    public ConfirmCodePresenter(ConfirmCodeActivity activity){
        this.activity = activity;
    }

    public void guardarDatosRegistro(String datosRegistro){
        String[] datosRegistroSeparado= datosRegistro.split("\\s+");
        mail = datosRegistroSeparado[0];
        password = datosRegistroSeparado[1];
        nombre = datosRegistroSeparado[2];
        apellido = datosRegistroSeparado[3];
        dni = datosRegistroSeparado[4];
        code = datosRegistroSeparado[5];
    }

    public boolean verificarCodigo(String codigo) {
        return codigo.equals(code);
    }

    public boolean hayConeccion(Context applicationContext) {
        return ControladorConeccion.checkConnection(applicationContext);
    }

    public void subirInformacion(Context context) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://so-unlam.net.ar/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitAPI rfApi = retrofit.create(RetrofitAPI.class);
            RegistroRequest dr = new RegistroRequest(nombre, apellido, Integer.parseInt(dni), mail, password, 2900, 6);
            Call<APIResponse> call = rfApi.postRegister(dr);
            call.enqueue(new Callback<APIResponse>() {

                @Override
                public void onResponse(@NonNull Call<APIResponse> call, @NonNull Response<APIResponse> response) {

                    APIResponse reg = response.body();
                    int code = response.code();

                    if(code == 200) {

                        mensajeRespuesta = "Registro completado";

                        assert reg != null;
                        SessionInfo.authToken = reg.getToken();
                        SessionInfo.refreshToken = reg.getToken_refresh();
                        SharedPreferences sharedp = context.getSharedPreferences("registroSharedPreferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedp.edit();
                        editor.putInt(mail, 1);
                        editor.apply();
                        activity.response(mensajeRespuesta);
                    } else {
                        try {
                            assert response.errorBody() != null;
                            JSONObject json =new JSONObject(response.errorBody().string());
                            mensajeRespuesta = json.get("msg").toString();
                            activity.response(mensajeRespuesta);
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFailure(@NonNull Call<APIResponse> call, @NonNull Throwable t) {
                    mensajeRespuesta = "Error al conectarse a la API";
                    activity.response(mensajeRespuesta);
                }
            });


        }


}
