package com.example.timemanager.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "projectSession_table")
public class ProjectSession {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int projectId;
    private String projectTitle;
    private long startTime, endTime;

    public ProjectSession(int projectId, String projectTitle, long startTime) {
        this.projectId = projectId;
        this.projectTitle = projectTitle;
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
