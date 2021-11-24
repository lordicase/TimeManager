package com.example.timemanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.timemanager.TaskRepository;
import com.example.timemanager.entity.Task;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private LiveData<List<Task>> allTask;

    public TaskViewModel(@NonNull Application application){
        super(application);
        repository = new TaskRepository(application);
        allTask = repository.getAllTask();
    }

    public void insert(Task task){repository.insert(task);}
    public void update(Task task){repository.update(task);}
    public void delete(Task task){repository.delete(task);}
    public LiveData<List<Task>> getAllTask(){return allTask;}
}