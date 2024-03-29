package com.example.timemanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.timemanager.entity.ProjectSession;
import com.example.timemanager.repository.ProjectSessionRepository;

import java.util.List;

public class ProjectSessionViewModel extends AndroidViewModel {
    private ProjectSessionRepository projectSessionRepository;

    public ProjectSessionViewModel (@NonNull Application application){
        super(application);
        projectSessionRepository = new ProjectSessionRepository(application);
        }

    public void insert(ProjectSession projectSession){projectSessionRepository.insert(projectSession);}
    public void updateEndTime(ProjectSession projectSession){projectSessionRepository.updateEndTime(projectSession);}
    public LiveData<List<ProjectSession>> getAllProjectSession(long startTime, long endTime){return projectSessionRepository.getAllProjectSession(startTime,endTime);}

}
