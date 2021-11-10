package com.example.timemanager;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProjectViewModel extends AndroidViewModel {
    private ProjectRepository repository;
    private LiveData<List<Project>> allProject;

    public ProjectViewModel(@NonNull Application application){
        super(application);
        repository = new ProjectRepository(application);
        allProject = repository.getAllProject();
    }

    public void insert(Project project){repository.insert(project);}
    public void update(Project project){repository.update(project);}
    public void delete(Project project){repository.delete(project);}
    public LiveData<List<Project>> getAllProject(){return allProject;}
}
