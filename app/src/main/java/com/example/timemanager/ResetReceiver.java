package com.example.timemanager;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.timemanager.repository.ProjectRepository;
import com.example.timemanager.ui.projects.ProjectsFragment;
import com.example.timemanager.viewmodel.ProjectViewModel;

public class ResetReceiver extends BroadcastReceiver {

    private ProjectRepository repository;
    @Override
    public void onReceive(Context context, Intent intent) {
        Application app = (Application) context.getApplicationContext();
        repository = new ProjectRepository((app));
        repository.resetTimeDone();
    }


}
