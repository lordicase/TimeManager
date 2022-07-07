package com.example.timemanager.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.timemanager.entity.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Project project);

    @Update
    void update(Project project);

    @Delete
    void delete(Project project);

    @Query("SELECT * FROM project_table")
    LiveData<List<Project>> getAllProjects();


    @Query("SELECT * FROM project_table WHERE days like :day")
    LiveData<List<Project>> getDayProjects(String day);

    @Query("SELECT id FROM project_table WHERE title=:title AND time=:time AND timeDone=:timeDone AND color=:color AND days=:days" )
    int getProjectId(String title,int time, int timeDone, String color, String days);

    @Query("UPDATE project_table set timeDone = 0")
    void resetTimeDone();

    @Query("UPDATE project_table SET timeDone = :timeDone WHERE id = :id")
    void setTimeDone(int timeDone, int id);
}
