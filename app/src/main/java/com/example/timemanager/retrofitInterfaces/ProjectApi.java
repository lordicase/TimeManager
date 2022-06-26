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
            @Field("userId") int userId,
            @Field("login") String login,
            @Field("password") String password,
            @Field("title") String title,
            @Field("time") int time,
            @Field("timeDone") int timeDone,
            @Field("color") String color,
            @Field("days") String days
    );


    @PATCH
    void update(Project project);

    @DELETE
    void delete(Project project);
}
