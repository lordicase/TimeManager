package com.example.timemanager.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "project_table", indices = {@Index(value = {"title","time","timeDone","color","days"}, unique = true)})
public class Project {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private int time, timeDone;
    private String color;
    private String days;

    public void setDays(String days) {
        this.days = days;
    }

    public String getDays() {
        return days;
    }

    public Project (String title, int time, String color){
        this.title = title;
        this.time = time;
        this.color = color;
    }
    @Ignore
    public Project (String title, int time, String color, String days){
        this.title = title;
        this.time = time;
        this.color = color;
        this.days = days;
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
