package com.example.timemanager.retrofitInterfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.timemanager.entity.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TaskApi {

    @FormUrlEncoded
    @POST("getAllTasks.php")
    Call<String> getAllTasks(@Field("userId") int userId,
                             @Field("login") String login,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("insertTask.php")
    Call<String> insertTask(@Field("userId") int userId,
                            @Field("login") String login,
                            @Field("password") String password,
                            @Field("id") int id,
                            @Field("projectId") int projectId,
                            @Field("title") String title,
                            @Field("projectTitle") String projectTitle,
                            @Field("color") String color,
                            @Field("done") Boolean done);

    @FormUrlEncoded
    @POST("updateTask.php")
    Call<String> updateTask(@Field("userId") int userId,
                            @Field("login") String login,
                            @Field("password") String password,
                            @Field("id") int id,
                            @Field("projectId") int projectId,
                            @Field("title") String title,
                            @Field("projectTitle") String projectTitle,
                            @Field("color") String color,
                            @Field("done") Boolean done);

    @FormUrlEncoded
    @POST("deleteTask.php")
    Call<String> deleteTask(@Field("userId") int userId,
                            @Field("login") String login,
                            @Field("password") String password,
                            @Field("id") int id);

}
