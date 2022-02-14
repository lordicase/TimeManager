package com.example.timemanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.timemanager.repository.TaskRepository;
import com.example.timemanager.entity.Task;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private LiveData<List<Task>> allTask;

    public TaskViewModel(@NonNull Application application){
        super(application);
        repository = new TaskRepository(application);

    }

    public void insert(Task task){repository.insert(task);}
    public void update(Task task){repository.update(task);}
    public void delete(Task task){repository.delete(task);}
    public LiveData<List<Task>> getAllTask(){return repository.getAllTask();}
    public LiveData<List<Task>> getNotDoneTasks(){return repository.getNotDoneTasks();}
    public LiveData<List<Task>> getAllProjectTasks(String project){return repository.getAllProjectTasks(project);}
    public LiveData<List<Task>> getNotDoneProjectTasks(String project){return repository.getNotDoneProjectTasks(project);}
    public void deleteProjectTasks(String projectTitle){repository.deleteProjectTasks(projectTitle);}
    public void updateTaskProjectTitle(int projectId, String newProjectTitle, String color){repository.updateTaskProjectTitle(projectId,newProjectTitle,color);}
    public void isDoneChange(boolean done, int id){repository.isDoneChange(done,id);}
}
