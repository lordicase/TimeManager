package com.example.timemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timemanager.retrofitInterfaces.LogRegisApi;
import com.example.timemanager.retrofitInterfaces.ProjectApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
    TextView textViewRegis;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        login = findViewById(R.id.editTextLogin);
        password = findViewById(R.id.editTextPassword);
        rememberBox = findViewById(R.id.checkBox);
        loginButton = findViewById(R.id.button5);
        textViewRegis = findViewById(R.id.textView9);


        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.getBoolean("rememberMe", false)) {
            rememberBox.setChecked(true);
            login.setText(sharedPreferences.getString("login", ""));
            password.setText(sharedPreferences.getString("password", ""));

        }
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        okhttp3.logging.HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.server_address) + "LogRegis/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
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
                        if (rememberBox.isChecked()) {
                            editor.putBoolean("rememberMe", true);
                        }
                        editor.putString("login", login.getText().toString());
                        editor.putString("password", password.getText().toString());
                        editor.putInt("id", Integer.parseInt(response.body()));
                        editor.apply();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Connection fail, check yours internet connection or use offline mode", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        textViewRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }


}