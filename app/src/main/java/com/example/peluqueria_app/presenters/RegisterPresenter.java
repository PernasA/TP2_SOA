package com.example.peluqueria_app.presenters;

import android.text.TextUtils;
import android.util.Patterns;

import com.example.peluqueria_app.ui.views.JavaMailAPI;
import com.example.peluqueria_app.ui.views.RegisterActivity;

import java.util.Random;

public class RegisterPresenter {
    final RegisterActivity activity;
    Random myRandom;
    public RegisterPresenter(RegisterActivity activity){
        this.activity = activity;
        myRandom = new Random();
    }

    public int sendEmail(String mail){
        int random = myRandom.nextInt(10000);
        JavaMailAPI javaMailAPI = new JavaMailAPI("agustinbaltazarpernas@gmail.com", random);
        javaMailAPI.execute();
        return random;
    }

    public int verificarCampos(String mail, String password) {
        if(mail == null || mail.isEmpty())return 991;
        if(password == null || password.isEmpty())return 992;
        if(password.length()< 8) return 881;
        if(isNotValidEmail(mail))return 882;
        return 0;
    }
    public int verificarCampos(String mail, String password, String nombre, String apellido, String dni) {
        if(mail == null || mail.isEmpty())return 991;
        if(password == null || password.isEmpty())return 992;
        if(nombre == null || nombre.isEmpty())return 993;
        if(apellido == null || apellido.isEmpty())return 994;
        if(dni == null || dni.isEmpty())return 995;
        if(password.length()< 8) return 881;
        if(isNotValidEmail(mail))return 882;
        if(dni.length() <7 || dni.length()>9) return 883;
        if(mail.contains(" ")|| password.contains(" ")||nombre.contains(" ")||apellido.contains(" ")||dni.contains(" ")) return 884;
        return 0;
    }
    public boolean isNotValidEmail(CharSequence target) {
        return !(!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
