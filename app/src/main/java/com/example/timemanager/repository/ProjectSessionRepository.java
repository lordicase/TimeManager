package com.example.timemanager.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.timemanager.DataBase;
import com.example.timemanager.dao.ProjectSessionDao;

import com.example.timemanager.entity.ProjectSession;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProjectSessionRepository {
    private ProjectSessionDao projectSessionDao;

    final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ProjectSessionRepository(Application application) {
        DataBase dataBase = DataBase.getInstance(application);
        projectSessionDao = dataBase.projectSessionDao();
    }

    public void insert(ProjectSession projectSession) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                projectSessionDao.insert(projectSession);
            }
        });
    }

    public void updateEndTime(int id, long endTime, long startTime ) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                projectSessionDao.updateEndTime(id, endTime, startTime);
            }
        });
    }

    public LiveData<List<ProjectSession>> getAllProjectSession(long startTime, long endTime) {
        return projectSessionDao.getAllProjectsSession(startTime, endTime);
    }
}