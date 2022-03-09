package com.example.timemanager.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.timemanager.DataBase;
import com.example.timemanager.dao.ProjectDao;
import com.example.timemanager.entity.Project;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProjectRepository {
    private ProjectDao projectDao;
    private LiveData<List<Project>> allProject;

    final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ProjectRepository(Application application) {
        DataBase dataBase = DataBase.getInstance(application);
        projectDao = dataBase.projectDao();
        allProject = projectDao.getAllProjects();


    }

    public void insert(Project project) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                projectDao.insert(project);
            }
        });
    }

    public void update(Project project) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                projectDao.update(project);
            }
        });
    }

    public void delete(Project project) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                projectDao.delete(project);
            }
        });
    }

    public void resetTimeDone() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                projectDao.resetTimeDone();
            }
        });
    }

    public void setTimeDone(int timeDone, int id) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                projectDao.setTimeDone(timeDone, id);
            }
        });
    }


    public LiveData<List<Project>> getAllProject() {
        return allProject;
    }
    public LiveData<List<Project>> getDayProject(String day) {
        return projectDao.getDayProjects(day);
    }
}
