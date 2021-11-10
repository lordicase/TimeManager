package com.example.timemanager;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "project_table")
public class Project {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private int time, time_done;
    private String color;

    public Project (String title, int time, String color){
        this.title = title;
        this.time = time;
        this.color = color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime_done(int time_done) {
        this.time_done = time_done;
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

    public int getTime_done() {
        return time_done;
    }

    public String getColor() {
        return color;
    }
}
