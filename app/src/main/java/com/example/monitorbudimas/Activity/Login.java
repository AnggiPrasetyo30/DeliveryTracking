package com.example.monitorbudimas.Activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.monitorbudimas.Model.user.responseLogin;
import com.example.monitorbudimas.Model.user.login;
import com.example.monitorbudimas.API.APIreq;
import com.example.monitorbudimas.API.retroServer;
import com.example.monitorbudimas.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText etUsername, etPassword;
    Button btnLogin;
    String Username, Password;
    APIreq APIreq;
    com.example.monitorbudimas.Adapter.Session sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.psw);

        btnLogin = findViewById(R.id.login);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                Username = etUsername.getText().toString();
                Password = etPassword.getText().toString();
                login(Username,Password);
                break;
        }
    }

    private void login(String username, String password) {
        APIreq = retroServer.konekRetrofit().create(APIreq.class);
        Call<responseLogin> loginCall = APIreq.loginResponse(username,password);
        loginCall.enqueue(new Callback<responseLogin>() {
            @Override
            public void onResponse(Call<responseLogin> call, Response<responseLogin> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){

                    // Ini untuk menyimpan sesi
                    sessionManager = new com.example.monitorbudimas.Adapter.Session(Login.this);
                    login loginData = response.body().getResponseLogin();
                    sessionManager.createLoginSession(loginData);

                    //Ini untuk pindah
//                    Toast.makeText(Login.this, response.body().getResponseLogin().getUsername(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Welcome.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, response.body().getPesan(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<responseLogin> call, Throwable t) {
                Toast.makeText(Login.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}