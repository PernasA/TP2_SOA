package com.example.peluqueria_app.presenters;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.peluqueria_app.Utils.ControladorConeccion;
import com.example.peluqueria_app.Utils.SessionInfo;
import com.example.peluqueria_app.models.APIResponse;
import com.example.peluqueria_app.models.MySharedPreferences;
import com.example.peluqueria_app.models.MySingletonRetrofit;
import com.example.peluqueria_app.models.RegistroRequest;
import com.example.peluqueria_app.ui.views.ConfirmCodeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

            RegistroRequest registroRequest = new RegistroRequest(nombre, apellido, Integer.parseInt(dni), mail, password, 2900, 6);
            Call<APIResponse> call = MySingletonRetrofit.getRetrofitApi().postRegister(registroRequest);
            call.enqueue(new Callback<APIResponse>() {

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onResponse(@NonNull Call<APIResponse> call, @NonNull Response<APIResponse> response) {

                    APIResponse reg = response.body();
                    int code = response.code();

                    if(code == 200) {
                        mensajeRespuesta = "Registro completado";
                        assert reg != null;
                        SessionInfo.authToken = reg.getToken();
                        SessionInfo.refreshToken = reg.getToken_refresh();
                        MySharedPreferences.registrarRegistroInicial(context,mail,activity);
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
