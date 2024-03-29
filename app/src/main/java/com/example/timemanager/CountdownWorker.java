package com.example.timemanager;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.ForegroundInfo;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.timemanager.entity.Project;
import com.example.timemanager.entity.ProjectSession;
import com.example.timemanager.ui.projects.ProjectsFragment;
import com.example.timemanager.viewmodel.ProjectSessionViewModel;
import com.example.timemanager.viewmodel.ProjectViewModel;

import java.util.Date;

public class CountdownWorker extends Worker {

    private NotificationManager notificationManager;
    ProjectViewModel projectViewModel;
    ProjectSessionViewModel projectSessionViewModel;

    Data inputData;
    Project project;
    String CHANNEL_ID = "CHANNEL_ID";
    String progress;
    int startedWorker, startedPosition;

    public CountdownWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            notificationManager = (NotificationManager)
                    context.getSystemService(NOTIFICATION_SERVICE);
        }

        inputData = getInputData();
        project = new Project(inputData.getString("title"), inputData.getInt("time", 0), inputData.getString("color"));
        project.setId(inputData.getInt("id", -1));
        project.setTimeDone(inputData.getInt("timeDone", 0));
        project.setDays(inputData.getString("days"));
        projectViewModel = ProjectsFragment.getProjectViewModel();
        projectSessionViewModel = ProjectsFragment.getProjectSessionViewModel();
        startedPosition = inputData.getInt("startedPosition", -1);
        startedWorker = inputData.getInt("startedWorker", 0);
    }

    @NonNull
    @Override
    public Result doWork() {

        // Mark the Worker as important
        setForegroundAsync(createForegroundInfo(progress));
        startCountdown();
        return Result.success();
    }

    private void startCountdown() {
        int count = (project.getTime() - project.getTimeDone()) / 1000;
        for (int i = 0; i < count; i++) {

            if (!isStopped()) {

                try {
                    project.setTimeDone(project.getTimeDone() + 1000);
                    projectViewModel.setTimeDone(project.getTimeDone(), project.getId());

                    progress = getTime(project.getTimeDone()) + " : " + getTime(project.getTime());

                    setForegroundAsync(createForegroundInfo(progress));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
        onStopped();
    }

    @NonNull
    private ForegroundInfo createForegroundInfo(@NonNull String progress) {
        // Build a notification using bytesRead and contentLength

        Context context = getApplicationContext();


        // This PendingIntent can be used to cancel the worker
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.putExtra("startedPosition", startedPosition);
        resultIntent.putExtra("startedWorker", startedWorker);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent intent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(intent)
                .setContentTitle(progress)
                .setTicker(progress)
                .setSmallIcon(R.drawable.ic_timer)
                .setOngoing(true)
                .setSilent(true)
                // Add the cancel action to the notification which can
                // be used to cancel the worker

                .build();

        return new ForegroundInfo(11, notification, FOREGROUND_SERVICE_TYPE_LOCATION);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel() {
        // Create a Notification channel
        CharSequence name = "channel_name";
        String description = "channel_description";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        notificationManager = (NotificationManager)
                getApplicationContext().getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public void onStopped() {
        ProjectSession projectSession = new ProjectSession(project.getId(), project.getTitle(), inputData.getLong("sessionStartTime", -1));
        projectSession.setEndTime(new Date().getTime());
        projectSessionViewModel.updateEndTime(projectSession);
        super.onStopped();
    }

    private String getTime(int time) {
        time /= 1000;
        String timeS;
        if (time >= 86400) {
            timeS = time / 86400 + "d " + time % 86400 / 3600 + "h";
        } else if (time >= 3600) {
            timeS = time / 3600 + "h " + time % 3600 / 60 + "min";
        } else if (time >= 60) {
            timeS = time / 60 + "min " + time % 60 + "s";
        } else {
            timeS = time % 60 + "s";
        }
        return timeS;
    }
}
