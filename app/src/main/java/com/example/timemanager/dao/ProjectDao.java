package com.example.timemanager.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.timemanager.entity.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert
    void insert(Project project);

    @Update
    void update(Project project);

    @Delete
    void delete(Project project);

    @Query("SELECT * FROM project_table")
    LiveData<List<Project>> getAllProjects();


    @Query("SELECT * FROM project_table WHERE days like :day")
    LiveData<List<Project>> getDayProjects(String day);
}
