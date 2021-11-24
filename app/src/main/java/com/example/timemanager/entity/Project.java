package com.example.timemanager.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "project_table")
public class Project {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private int time, timeDone;
    private String color;

    public Project (String title, int time, String color){
        this.title = title;
        this.time = time;
        this.color = color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimeDone(int timeDone) {
        this.timeDone = timeDone;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getTime() {
        return time;
    }

    public int getTimeDone() {
        return timeDone;
    }

    public String getColor() {
        return color;
    }
}
