package com.example.timemanager;

import android.app.Application;

import androidx.lifecycle.LiveData;

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
        allTasks = taskDao.getAllTasks();
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
        return allTasks;
    }
}
