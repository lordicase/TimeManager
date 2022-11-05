package com.example.timemanager.retrofitInterfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.timemanager.entity.ProjectSession;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProjectSessionApi {

    @FormUrlEncoded
    @POST("insertProjectSession.php")
    Call<String> insertProjectSession(@Field("id") int id,
                                      @Field("userId") int userId,
                                      @Field("login") String login,
                                      @Field("password") String password,
                                      @Field("projectId") int projectId,
                                      @Field("projectTitle") String projectTitle,
                                      @Field("startTime") long startTime,
                                      @Field("endTime") long endTime);


    @FormUrlEncoded
    @POST("getAllProjectSession.php")
    Call<String> getProjectsSession(@Field("userId") int userId,
                                    @Field("login") String login,
                                    @Field("password") String password,
                                    @Field("startTime") long startTime,
                                    @Field("endTime") long endTime);
}

