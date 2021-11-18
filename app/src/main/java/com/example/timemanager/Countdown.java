package com.example.timemanager;

import android.app.Application;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;

import java.security.acl.Owner;

public class Countdown extends CountDownTimer implements ViewModelStoreOwner {
    ProjectViewModel projectViewModel;
    int position;

Project project;
    public Countdown(long millisInFuture, long countDownInterval, Project project, int position) {
        super(millisInFuture, countDownInterval);
        this.project = project;
        this.position  = position;
        projectViewModel = MainActivity.projectViewModel;
    }

    @Override
    public void onTick(long l) {
        project.setTimeDone(project.getTimeDone()+1000);
        projectViewModel.update(project);
        Log.d("TESTSSS","TESTSSS");
    }

    @Override
    public void onFinish() {

    }





    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return null;
    }
}