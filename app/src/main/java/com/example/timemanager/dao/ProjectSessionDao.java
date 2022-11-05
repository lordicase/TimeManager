package com.example.timemanager.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.timemanager.entity.ProjectSession;

import java.util.List;

@Dao
public interface ProjectSessionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ProjectSession projectSession);

    @Query("UPDATE projectSession_table SET endTime = :endTime WHERE projectId = :id AND startTime = :startTime")
    void updateEndTime(int id, long endTime, long startTime);

    @Query("SELECT id FROM projectSession_table WHERE projectId=:projectId AND projectTitle=:projectTitle AND startTime=:startTime AND endTime=:endTime")
    int getProjectSessionId(int projectId, String projectTitle, long startTime, long endTime);

    @Query("SELECT * FROM projectSession_table WHERE endTime != 0 AND startTime>:startTime AND endTime<:endTime ORDER BY projectId DESC, startTime DESC ")
    LiveData<List<ProjectSession>> getAllProjectsSession(long startTime, long endTime);
}
