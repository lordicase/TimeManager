package com.example.timemanager;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class CountdownService extends Service {
    private Looper serviceLooper;
    private ServiceHandler serviceHandler;
    String CHANNEL_ID = "CHANNEL_ID";
    private NotificationManager notificationManager;
    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    createChannel();
                }
                for (int i = 0; i <= 60; i++) {
                    Log.d("CountdownWorker", "run: ");
//                    Notification notification = new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
//                                .setContentTitle("SERVIS")
//                                .setContentText("DZIAła jeszcze")
//                                .setSmallIcon(R.drawable.ic_save)
//                                .setOngoing(true)
//                                .setPriority(NotificationCompat.PRIORITY_MAX)
//                                .build();
//                                // Add the cancel action to the notification which can
//                                // be used to cancel the worker
//                            //    .addAction(android.R.drawable.ic_delete, cancel, intent)
//                        notificationManager.notify(5, notification);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                // Restore interrupt status.
                Thread.currentThread().interrupt();
            }
            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        Log.d("LOGTAG", "NotificationService.onCreate()...");
        // Start up the thread running the service. Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block. We also make it
        // background priority so CPU-intensive work doesn't disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                THREAD_PRIORITY_BACKGROUND);

        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("SERVIS")
                .setContentText("już nie działa")
                .setSmallIcon(R.drawable.ic_save)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();
        // Add the cancel action to the notification which can
        // be used to cancel the worker
        //    .addAction(android.R.drawable.ic_delete, cancel, intent)
        notificationManager.notify(6, notification);
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel() {
        CharSequence name = "channel_name";
        String description = "channel_description";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        notificationManager = (NotificationManager)
                this.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
