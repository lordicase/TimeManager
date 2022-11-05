package com.example.timemanager.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;

import com.example.timemanager.DataBase;
import com.example.timemanager.R;
import com.example.timemanager.dao.TaskDao;
import com.example.timemanager.entity.Task;
import com.example.timemanager.retrofitInterfaces.TaskApi;
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

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;
    TaskApi taskApi;
    final ExecutorService executorService = Executors.newSingleThreadExecutor();
    SharedPreferences sharedPreferences;

    public TaskRepository(Application application) {
        DataBase dataBase = DataBase.getInstance(application);
        taskDao = dataBase.taskDao();
        sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(application.getString(R.string.server_address) + "Task/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        taskApi = retrofit.create(TaskApi.class);
    }

    public void insert(Task task) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.insert(task);
                int taskId = taskDao.getProjectId(task.getTitle(), task.getProjectId(), task.getProjectTitle(), task.getColor(), task.isDone());
                task.setId(taskId);
                insertTaskOnServer(task);
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
                deleteTaskFromServer(task.getId());
            }
        });
    }

    public LiveData<List<Task>> getAllTask() {
        return taskDao.getAllTasks();
    }

    public LiveData<List<Task>> getNotDoneTasks() {
        return taskDao.getNotDoneTasks();
    }

    public LiveData<List<Task>> getAllProjectTasks(String project) {
        return taskDao.getAllProjectTasks(project);
    }

    public LiveData<List<Task>> getNotDoneProjectTasks(String project) {
        return taskDao.getNotDoneProjectTasks(project);
    }

    public void deleteProjectTasks(String projectTitle) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.deleteProjectTasks(projectTitle);
            }
        });
    }

    public void updateTaskProjectTitle(int projectId, String newProjectTitle, String color) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.updateTaskProjectTitle(projectId, newProjectTitle, color);
            }
        });
    }

    public void isDoneChange(boolean done, int id) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.isDoneChange(done, id);
            }
        });
    }

    public void insertTaskOnServer(Task task) {
        Call<String> call = taskApi.insertTask(sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("login", ""),
                sharedPreferences.getString("password", ""),
                task.getId(),
                task.getProjectId(),
                task.getTitle(),
                task.getProjectTitle(),
                task.getColor(),
                task.isDone());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void updateTaskOnServer(Task task) {
        Call<String> call = taskApi.updateTask(sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("login", ""),
                sharedPreferences.getString("password", ""),
                task.getId(),
                task.getProjectId(),
                task.getTitle(),
                task.getProjectTitle(),
                task.getColor(),
                task.isDone());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void getAllTaskFromServer() {
        Call<String> call = taskApi.getAllTasks(sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("login", ""),
                sharedPreferences.getString("password", ""));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    public void deleteTaskFromServer(int taskId){
        Call<String> call = taskApi.deleteTask(sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("login", ""),
                sharedPreferences.getString("password", ""),
                taskId);
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

