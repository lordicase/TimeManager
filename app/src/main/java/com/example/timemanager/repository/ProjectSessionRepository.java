package com.example.timemanager.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.timemanager.DataBase;
import com.example.timemanager.R;
import com.example.timemanager.dao.ProjectSessionDao;

import com.example.timemanager.entity.ProjectSession;
import com.example.timemanager.retrofitInterfaces.ProjectApi;
import com.example.timemanager.retrofitInterfaces.ProjectSessionApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectSessionRepository {
    private ProjectSessionDao projectSessionDao;
    private ProjectSessionApi projectSessionApi;
    final ExecutorService executorService = Executors.newSingleThreadExecutor();
    SharedPreferences sharedPreferences;

    public ProjectSessionRepository(Application application) {
        DataBase dataBase = DataBase.getInstance(application);
        projectSessionDao = dataBase.projectSessionDao();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(application.getString(R.string.server_address) + "ProjectSession/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        projectSessionApi = retrofit.create(ProjectSessionApi.class);
        sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE);
    }

    public void insert(ProjectSession projectSession) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                projectSessionDao.insert(projectSession);
            }
        });
    }

    public void updateEndTime(ProjectSession projectSession) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                projectSessionDao.updateEndTime(projectSession.getProjectId(), projectSession.getEndTime(), projectSession.getStartTime());
                int projectSessionId = projectSessionDao.getProjectSessionId(projectSession.getProjectId(), projectSession.getProjectTitle(), projectSession.getStartTime(), projectSession.getEndTime());
                projectSession.setId(projectSessionId);
                insertProjectSessionOnServer(projectSession);
            }
        });
    }

    public LiveData<List<ProjectSession>> getAllProjectSession(long startTime, long endTime) {
        return projectSessionDao.getAllProjectsSession(startTime, endTime);
    }

    public void insertProjectSessionOnServer(ProjectSession projectSession) {
        Call<String> call = projectSessionApi.insertProjectSession(projectSession.getId(), sharedPreferences.getInt("id", -1), sharedPreferences.getString("login", ""), sharedPreferences.getString("password", ""), projectSession.getProjectId(), projectSession.getProjectTitle(), projectSession.getStartTime(), projectSession.getEndTime());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
  
                return;
            }


            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void getProjectSessionFromServer(long startTime, long endTime){
        Call<String> call = projectSessionApi.getProjectsSession(sharedPreferences.getInt("id",-1), sharedPreferences.getString("login",""),sharedPreferences.getString("password",""),startTime,endTime );
    call.enqueue(new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {

        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {

        }
    });
    }
}