package com.example.timemanager.retrofitInterfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.timemanager.entity.ProjectSession;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProjectSessionApi {

    @POST
    void insert(ProjectSession projectSession);


    @Query("192.168.0.2/hgh")
    void updateEndTime(int id, long endTime, long startTime);

    @GET("SELECT * FROM projectSession_table WHERE endTime != 0 AND startTime>:startTime AND endTime<:endTime ORDER BY projectId DESC, startTime DESC ")
    Call <List<ProjectSession>> getAllProjectsSession(@Field("startTime") long startTime, @Field("endTime") long endTime);
}
