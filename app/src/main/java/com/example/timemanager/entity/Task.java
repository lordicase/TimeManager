package com.example.timemanager.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private int projectId;
    private boolean done;

    public Task(String title, int projectId, boolean done) {
        this.title = title;
        this.projectId = projectId;
        this.done = done;
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
}
