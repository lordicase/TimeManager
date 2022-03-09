package com.example.timemanager.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.timemanager.entity.Project;
import com.example.timemanager.entity.ProjectSession;

import java.util.List;

@Dao
public interface ProjectSessionDao {

    @Insert
    void insert(ProjectSession projectSession);

    @Query("UPDATE projectSession_table SET endTime = :endTime WHERE projectId = :id AND startTime = :startTime")
    void updateEndTime(int id, long endTime, long startTime);

    @Query("SELECT * FROM projectSession_table ORDER BY projectId DESC, startTime DESC")
    LiveData<List<ProjectSession>> getAllProjectsSession();
}
