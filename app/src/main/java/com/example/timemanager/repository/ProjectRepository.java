package com.example.timemanager.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.timemanager.DataBase;
import com.example.timemanager.R;
import com.example.timemanager.dao.ProjectDao;
import com.example.timemanager.entity.Project;
import com.example.timemanager.retrofitInterfaces.ProjectApi;
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

public class ProjectRepository {
    private ProjectDao projectDao;
    private LiveData<List<Project>> allProject;
    private ProjectApi projectApi;
    final ExecutorService executorService = Executors.newSingleThreadExecutor();
    List<Project> projects;
    SharedPreferences sharedPreferences;

    public ProjectRepository(Application application) {
        DataBase dataBase = DataBase.getInstance(application);
        projectDao = dataBase.projectDao();
        allProject = projectDao.getAllProjects();
        sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(application.getString(R.string.server_address)+"timemanagerAPI/Project/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        projectApi = retrofit.create(ProjectApi.class);
    }

    public void insert(Project project) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                projectDao.insert(project);
            }
        });

        Call<String> call = projectApi.insertProject(sharedPreferences.getInt("id",-1), sharedPreferences.getString("login",""), sharedPreferences.getString("password",""), project.getTitle(), project.getTime(), project.getTimeDone(), project.getColor(), project.getDays());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {

                    return;
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void update(Project project) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                projectDao.update(project);
            }
        });
    }

    public void delete(Project project) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                projectDao.delete(project);
            }
        });
    }

    public void resetTimeDone() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                projectDao.resetTimeDone();
            }
        });
    }

    public void setTimeDone(int timeDone, int id) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                projectDao.setTimeDone(timeDone, id);
            }
        });
    }


    public LiveData<List<Project>> getAllProject() {
        return allProject;
    }

    public LiveData<List<Project>> getDayProject(String day) {
        return projectDao.getDayProjects(day);
    }

    public List<Project> getProject() {
        Call<List<Project>> call = projectApi.getProjects(sharedPreferences.getInt("id",-1), sharedPreferences.getString("login",""), sharedPreferences.getString("password",""));

        call.enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {

            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {

            }
        });

        return projects;
    }
}
