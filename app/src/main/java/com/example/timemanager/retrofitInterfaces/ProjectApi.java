package com.example.timemanager.retrofitInterfaces;


import com.example.timemanager.entity.Project;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProjectApi {

    @GET("getAllProjects.php")
    Call<List<Project>> getProjects(
            @Field("userId") int userId,
            @Field("login") String login,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("insertProject.php")
    Call<String> insertProject(
            @Field("id") int id,
            @Field("userId") int userId,
            @Field("login") String login,
            @Field("password") String password,
            @Field("title") String title,
            @Field("time") int time,
            @Field("timeDone") int timeDone,
            @Field("color") String color,
            @Field("days") String days
    );


    @FormUrlEncoded
    @POST("updateProject.php")
    Call<String> updateProject(
            @Field("id") int id,
            @Field("userId") int userId,
            @Field("login") String login,
            @Field("password") String password,
            @Field("title") String title,
            @Field("time") int time,
            @Field("timeDone") int timeDone,
            @Field("color") String color,
            @Field("days") String days
    );

    @FormUrlEncoded
    @POST("deleteProject.php")
    Call<String> deleteProject(
            @Field("userId") int userId,
            @Field("login") String login,
            @Field("password") String password,
            @Field("id") int id);
}
