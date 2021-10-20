package com.example.peluqueria_app.presenters;

import com.example.peluqueria_app.ui.login.JavaMailAPI;
import com.example.peluqueria_app.ui.login.RegisterActivity;

import java.util.Random;

public class RegisterPresenter {
    final RegisterActivity activity;
    Random myRandom;
    public RegisterPresenter(RegisterActivity activity){
        this.activity = activity;
        myRandom = new Random();
    }

    public void sendEmail(String email){

        JavaMailAPI javaMailAPI = new JavaMailAPI("agustinbaltazarpernas@gmail.com",myRandom.nextInt(10000) );
        javaMailAPI.execute();
    }
}
