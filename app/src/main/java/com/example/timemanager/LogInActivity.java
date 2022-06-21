package com.example.timemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timemanager.retrofitInterfaces.LogRegisApi;
import com.example.timemanager.retrofitInterfaces.ProjectApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogInActivity extends AppCompatActivity {
    Button loginButton;
    EditText login, password;
    CheckBox rememberBox;
    LogRegisApi logRegisApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        login = findViewById(R.id.editTextLogin);
        password = findViewById(R.id.editTextPassword);
        rememberBox = findViewById(R.id.checkBox);
        loginButton = findViewById(R.id.button5);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.server_address)+"timemanagerAPI/LogRegis/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        logRegisApi = retrofit.create(LogRegisApi.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please insert login and password", Toast.LENGTH_SHORT).show();
                    return;
                }
                Call<String> call = logRegisApi.login(login.getText().toString(), password.getText().toString());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().equals("invalid data")) {
                            Toast.makeText(getApplicationContext(), response.body(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                      //  finish();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Connection fail, check yours internet connection or use offline mode", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }


}