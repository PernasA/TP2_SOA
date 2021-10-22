package com.example.peluqueria_app.APIs;

import com.example.peluqueria_app.models.APIResponse;
import com.example.peluqueria_app.models.EventRequest;
import com.example.peluqueria_app.models.EventResponse;
import com.example.peluqueria_app.models.LoginRequest;
import com.example.peluqueria_app.models.RegistroRequest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public interface RetrofitAPI {

    @POST("api/api/register")
    Call<APIResponse> postRegister(@Body RegistroRequest dr);

    @POST("api/api/login")
    Call<APIResponse> postLogin(@Body LoginRequest lr);

    @PUT("api/api/refresh")
    Call<APIResponse> putRefreshToken(@HeaderMap Map<String, String> headers);

    @POST("api/api/event")
    Call<EventResponse> postEvent(@HeaderMap Map<String, String> headers, @Body EventRequest er);

}
