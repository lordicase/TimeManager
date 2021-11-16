package com.example.timemanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class CountdownWorker extends Worker {
    private NotificationManager notificationManager;
    ProjectViewModel projectViewModel;
    Data imputData;
    Project project;
    String CHANNEL_ID = "CHANNEL_ID";
    public CountdownWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_name";
            String description = "channel_description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager = (NotificationManager)
                    context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        imputData = getInputData();
        project = new Project(imputData.getString("title"), imputData.getInt("time",0), imputData.getString("color"));
        project.setId(imputData.getInt("id",-1));
        project.setTimeDone(imputData.getInt("timeDone",0));
        projectViewModel = MainActivity.projectViewModel;
    }

    @NonNull
    @Override
    public Result doWork() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= ((project.getTime()- project.getTimeDone())/1000); i++) {
                    Log.d("CountdownWorker", "run: " + i);
                    try {

                        project.setTimeDone(project.getTimeDone()+1000);
                        projectViewModel.update(project);



                        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                .setContentTitle("title")
                                .setContentText("title")
                                .setSmallIcon(R.drawable.ic_save)
                                .setOngoing(true)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .build();
                                // Add the cancel action to the notification which can
                                // be used to cancel the worker
                            //    .addAction(android.R.drawable.ic_delete, cancel, intent)
                        notificationManager.notify(i, notification);

                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.d("CountdownWorker", "Job finished");

            }
        }).start();
        return Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

    }
}
