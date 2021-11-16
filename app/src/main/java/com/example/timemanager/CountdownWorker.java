package com.example.timemanager;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION;
import static android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MICROPHONE;
import static android.os.Process.THREAD_PRIORITY_BACKGROUND;
import static android.os.Process.THREAD_PRIORITY_VIDEO;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.ForegroundInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class CountdownWorker extends Worker {
    private static final String KEY_INPUT_URL = "KEY_INPUT_URL";
    private static final String KEY_OUTPUT_FILE_NAME = "KEY_OUTPUT_FILE_NAME";
    private NotificationManager notificationManager;
    ProjectViewModel projectViewModel;
    Data imputData;
    Project project;
    String CHANNEL_ID = "CHANNEL_ID";
    int notificationId = 0;

    public CountdownWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            notificationManager = (NotificationManager)
                    context.getSystemService(NOTIFICATION_SERVICE);
        }

        imputData = getInputData();
        project = new Project(imputData.getString("title"), imputData.getInt("time", 0), imputData.getString("color"));
        project.setId(imputData.getInt("id", -1));
        project.setTimeDone(imputData.getInt("timeDone", 0));
        projectViewModel = MainActivity.projectViewModel;
    }

    @NonNull
    @Override
    public Result doWork() {
        Data inputData = getInputData();
        String inputUrl = inputData.getString(KEY_INPUT_URL);
        String outputFile = inputData.getString(KEY_OUTPUT_FILE_NAME);
        // Mark the Worker as important
        String progress = "Starting Download";
        setForegroundAsync(createForegroundInfo(progress));
        download(inputUrl, outputFile);

        return Result.success();
    }

    private void download(String inputUrl, String outputFile) {
        for (int i = 0; i <= ((project.getTime() - project.getTimeDone()) / 1000); i++) {
            Log.d("CountdownWorker", "run: " + i);
            try {
                notificationId++;
                project.setTimeDone(project.getTimeDone() + 1000);
                projectViewModel.update(project);
                setForegroundAsync(createForegroundInfo(String.valueOf(project.getTimeDone())));
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    @NonNull
    private ForegroundInfo createForegroundInfo(@NonNull String progress) {
        // Build a notification using bytesRead and contentLength

        Context context = getApplicationContext();
        String id = "notification_channel_id";
        String title = progress;
        String cancel = "cancel_download";
        // This PendingIntent can be used to cancel the worker
        PendingIntent intent = WorkManager.getInstance(context)
                .createCancelPendingIntent(getId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setTicker(title)
                .setSmallIcon(R.drawable.ic_round_pause)
                .setOngoing(true)
                .setSilent(true)
                // Add the cancel action to the notification which can
                // be used to cancel the worker
                .addAction(android.R.drawable.ic_delete, cancel, intent)
                .build();

        return new ForegroundInfo(11, notification,FOREGROUND_SERVICE_TYPE_LOCATION);
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
}
