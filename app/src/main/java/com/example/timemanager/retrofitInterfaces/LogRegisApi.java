package com.example.timemanager.retrofitInterfaces;

import com.example.timemanager.entity.Project;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LogRegisApi {


    @FormUrlEncoded
    @POST("login.php")
    Call<String> login(
            @Field("login") String login,
            @Field("password") String password
    );

    @GET("register.php")
    Call<String> register(
            @Field("login") String login,
            @Field("password") String password
    );



}
