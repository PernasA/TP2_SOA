package com.example.peluqueria_app.models;

import com.example.peluqueria_app.APIs.RetrofitAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MySingletonRetrofit {
    private static MySingletonRetrofit instanciaUnica = new MySingletonRetrofit();
    private static final RetrofitAPI myRetrofitAPI = new Retrofit.Builder()
                                         .baseUrl("http://so-unlam.net.ar/")
                                         .addConverterFactory(GsonConverterFactory.create())
                                         .build().create(RetrofitAPI.class);
    private MySingletonRetrofit(){}

    public static MySingletonRetrofit getInstance(){
        if(instanciaUnica==null){
            instanciaUnica= new MySingletonRetrofit();
        }
        return instanciaUnica;
    }

    public static RetrofitAPI getRetrofitApi() {
        return  myRetrofitAPI;
    }

}
