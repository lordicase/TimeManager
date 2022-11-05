package com.example.timemanager.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table", indices = @Index(value = {"projectId", "title"}))
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private int projectId;
    private String projectTitle;
    private String color;
    private boolean done;



    public Task(String title, int projectId, boolean done, String projectTitle, String color) {
        this.title = title;
        this.projectId = projectId;
        this.done = done;
        this.projectTitle = projectTitle;
        this.color = color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getProjectId() {
        return projectId;
    }

    public boolean isDone() {
        return done;
    }

    public String getProjectTitle() {return projectTitle;}

    public String getColor() {
        return color;
    }
}
