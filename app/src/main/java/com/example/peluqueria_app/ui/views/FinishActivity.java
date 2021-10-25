package com.example.peluqueria_app.ui.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class FinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finishAffinity();
        System.exit(1);
    }
}