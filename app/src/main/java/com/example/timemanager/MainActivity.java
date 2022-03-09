package com.example.timemanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.timemanager.databinding.ActivityMainBinding;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    static int startedPosition = -8;
    static int startedWorker = 0;
    private ActivityMainBinding binding;
    private AlarmManager alarmManager;



    public static int getStartedPosition() {
        return startedPosition;
    }

    public static int getStartedWorker() {
        return startedWorker;
    }

    public static void setStartedPosition(int startedPosition) {
        MainActivity.startedPosition = startedPosition;
    }

    public static void setStartedWorker(int startedWorker) {
        MainActivity.startedWorker = startedWorker;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNewIntent(getIntent());
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_projects, R.id.navigation_all_tasks, R.id.navigation_all_stat)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this,ResetReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        Calendar resetTime = Calendar.getInstance();
        resetTime.set(Calendar.HOUR_OF_DAY,23);
        resetTime.set(Calendar.MINUTE, 59);
        resetTime.set(Calendar.SECOND, 0);

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, resetTime.getTimeInMillis(),pendingIntent);

    }


    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("startedPosition")) {

                // extract the extra-data in the Notification
                startedPosition = extras.getInt("startedPosition", -1);
                startedWorker = extras.getInt("startedWorker", 0);


            }
        }

    }






}