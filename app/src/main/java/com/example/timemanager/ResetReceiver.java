package com.example.timemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.timemanager.ui.projects.ProjectsFragment;
import com.example.timemanager.viewmodel.ProjectViewModel;

public class ResetReceiver extends BroadcastReceiver {
    ProjectViewModel projectViewModel;

    @Override
    public void onReceive(Context context, Intent intent) {
        projectViewModel= ProjectsFragment.getProjectViewModel();
        projectViewModel.resetTimeDone();
    }


}
