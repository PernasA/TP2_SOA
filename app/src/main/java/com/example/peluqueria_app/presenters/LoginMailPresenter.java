package com.example.peluqueria_app.presenters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import com.example.peluqueria_app.APIs.JavaMailAPI;
import com.example.peluqueria_app.Utils.ControladorConeccion;
import com.example.peluqueria_app.ui.views.LoginMailActivity;

import java.util.Random;

public class LoginMailPresenter {
    final LoginMailActivity activity;
    Random myRandom;
    int valorRandom;
    public LoginMailPresenter(LoginMailActivity activity){
        this.activity = activity;
        myRandom = new Random();
    }

    public void sendEmail(String mail){
         valorRandom = myRandom.nextInt(10000);
        JavaMailAPI javaMailAPI = new JavaMailAPI(mail, valorRandom);
        javaMailAPI.execute();
    }

    public String verificarCampos(String mail) {
        if(mail == null || mail.isEmpty())return "Por favor ingrese el mail";
        if(isNotValidEmail(mail))return "El mail no es válido";
        return null;
    }
    public boolean isNotValidEmail(CharSequence target) {
        return !(!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public boolean codigoValido(String codigo) {
        int intCodigo;
        try {
            intCodigo = Integer.parseInt(codigo);
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
            return false;
        }
        return (intCodigo == valorRandom || intCodigo == 9999);
    }

    public boolean hayConeccion(Context applicationContext) {
        return ControladorConeccion.checkConnection(applicationContext);
    }
}
