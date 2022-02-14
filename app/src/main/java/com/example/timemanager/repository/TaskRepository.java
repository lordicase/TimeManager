package com.example.timemanager.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.timemanager.DataBase;
import com.example.timemanager.dao.TaskDao;
import com.example.timemanager.entity.Task;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;

    final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public TaskRepository(Application application) {
        DataBase dataBase = DataBase.getInstance(application);
        taskDao = dataBase.taskDao();
    }

    public void insert(Task task) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.insert(task);
            }
        });
    }

    public void update(Task task) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.update(task);
            }
        });
    }

    public void delete(Task task) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.delete(task);
            }
        });
    }

    public LiveData<List<Task>> getAllTask() {
        return taskDao.getAllTasks();
    }
    public LiveData<List<Task>> getNotDoneTasks() { return taskDao.getNotDoneTasks(); }
    public LiveData<List<Task>> getAllProjectTasks(String project) { return taskDao.getAllProjectTasks(project); }
    public LiveData<List<Task>> getNotDoneProjectTasks(String project) { return taskDao.getNotDoneProjectTasks(project); }

    public void deleteProjectTasks(String projectTitle) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.deleteProjectTasks(projectTitle);
            }
        });
    }

    public void updateTaskProjectTitle(int projectId, String newProjectTitle, String color) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.updateTaskProjectTitle(projectId, newProjectTitle, color);
            }
        });
    }

    public void isDoneChange(boolean done, int id){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.isDoneChange(done, id);
            }
        });
    }
}

