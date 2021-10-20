package com.example.peluqueria_app.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.peluqueria_app.R;
import com.example.peluqueria_app.databinding.ActivityRegisterBinding;
import com.example.peluqueria_app.presenters.RegisterPresenter;


public class RegisterActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText=(EditText)findViewById(R.id.editTextMail);
    passwordEditText=(EditText)findViewById(R.id.editTextPassword);
        Button loginButton = (Button) findViewById(R.id.botonLogin);
        loginButton.setOnClickListener(botonesListeners);
        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(botonesListeners);
        Log.i("Ejecuto","Ejecuto onCreate");

    }
    @Override
    protected void onStart()
    {
        Log.i("Ejecuto","Ejecuto Onstart");
        super.onStart();
    }

    @Override
    protected void onResume()
    {
        Log.i("Ejecuto","Ejecuto OnResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("Ejecuto","Ejecuto OnPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Ejecuto","Ejecuto OnStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i("Ejecuto","Ejecuto OnRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Ejecuto","Ejecuto OnDestroy");
    }

    //Metodo que actua como Listener de los eventos que ocurren en los componentes graficos de la activty
    private final View.OnClickListener botonesListeners = v -> {
        Intent intent;

        //Se determina que componente genero un evento
        switch (v.getId())
        {
            //Si se ocurrio un evento en el boton OK
            case R.id.botonLogin:
                //se genera un Intent para poder lanzar la activity principal
                //intent=new Intent(MainActivity.this,DialogActivity.class);
                //Se le agrega al intent los parametros que se le quieren pasar a la activyt principal
                //cuando se lanzado
                //intent.putExtra("textoOrigen",txtOrigen.getText().toString());
                //se inicia la activity principal
                //startActivity(intent);
                break;
            case R.id.registerButton:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(getApplicationContext(),"Error en Listener de botones",Toast.LENGTH_LONG).show();
        }


    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RegisterPresenter presenter = new RegisterPresenter(this);
        // Get the Intent that started this activity and extract the string
        //Intent intent = getIntent();

        com.example.peluqueria_app.databinding.ActivityRegisterBinding binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.editTextMail;
        final EditText passwordEditText = binding.editTextMail;
        final Button confirmarButton = binding.buttonConfirmar;

        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }

            confirmarButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {

                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {

                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }
            if (loginResult.getSuccess() != null) {
                updateUiWithUser(loginResult.getSuccess());
            }
            setResult(Activity.RESULT_OK);

            //Complete and destroy login activity once successful
            finish();
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {

                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };

        usernameEditText.addTextChangedListener(afterTextChangedListener);

        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
            return false;
        });

        confirmarButton.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Se presiono confirmar", Toast.LENGTH_SHORT).show();
            presenter.sendEmail(usernameEditText.getText().toString());
        });

        //Intent intent = new Intent(this, RegisterActivity.class);
        //startActivity(intent);
    }
    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }


}

