package com.example.timemanager.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.timemanager.entity.Project;
import com.example.timemanager.entity.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * FROM task_table WHERE projectId like :project")
    LiveData<List<Task>> getProjectTasks(String project);

    @Query("DELETE FROM task_table WHERE projectTitle = :projectTitle")
    void deleteProjectTasks(String projectTitle);

    @Query("UPDATE task_table SET projectTitle = :newProjectTitle, color = :color WHERE projectTitle = :projectId")
    void updateTaskProjectTitle(int projectId, String newProjectTitle, String color);
}
