package com.example.timemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timemanager.retrofitInterfaces.LogRegisApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
EditText login,pass1,pass2;
Button regisButton;
LogRegisApi logRegisApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        login = findViewById(R.id.editTextLogin);
        pass1 = findViewById(R.id.editTextPassword);
        pass2 = findViewById(R.id.editTextPassword2);
        regisButton = findViewById(R.id.button7);

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

        regisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String log = login.getText().toString();
                String password1 = pass1.getText().toString();
                String password2 = pass2.getText().toString();
                if (log.trim().isEmpty()||password1.trim().isEmpty()||password2.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please insert all data", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!pass1.getText().toString().equals(pass2.getText().toString())){

                        Toast.makeText(getApplicationContext(), "Please insert correct password", Toast.LENGTH_SHORT).show();
                        return;
                    }

                Call<String> call = logRegisApi.register(login.getText().toString(),pass1.getText().toString());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if (response.body().equals("200")) {
                            Toast.makeText(getApplicationContext(),  "This account exist", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(getApplicationContext(),  "Registration correct, please login", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LogInActivity.class));
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