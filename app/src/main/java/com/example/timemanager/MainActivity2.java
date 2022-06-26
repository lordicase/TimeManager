package com.example.timemanager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.timemanager.databinding.ActivityMain2Binding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity2 extends AppCompatActivity {
    public static final String EXTRA_TITLE = "com.example.timemanager.EXTRA_TITLE";
    public static final String EXTRA_COLOR = "com.example.timemanager.EXTRA_COLOR";
    public static final String EXTRA_TIME = "com.example.timemanager.EXTRA_TIME";
    public static final String EXTRA_TIME_DONE = "com.example.timemanager.EXTRA_TIME_DONE";
    public static final String EXTRA_DAYS = "com.example.timemanager.EXTRA_DAYS";
    public static final String EXTRA_ID = "com.example.timemanager.EXTRA_ID";
    private static int id = -1;
    private static String color = "#F44336";
    private static String projectTitle, days;
    private static int hour, minutes, timeDone;
    private ActivityMain2Binding binding;

    public static String getDays() {
        return days;
    }

    public static String getColor() {
        return color;
    }

    public static String getProjectTitle() {
        return projectTitle;
    }

    public static int getHour() {
        return hour;
    }

    public static int getMinutes() {
        return minutes;
    }

    public static int getTimeDone() {
        return timeDone;
    }

    public static int getId() {
        return id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
            getSupportActionBar().setTitle("Edit project");
            id = intent.getIntExtra(EXTRA_ID, -1);
            color = intent.getStringExtra(EXTRA_COLOR);
            projectTitle = intent.getStringExtra(EXTRA_TITLE);
            days = intent.getStringExtra(EXTRA_DAYS);
            hour = intent.getIntExtra(EXTRA_TIME, 0) / 3600000;
            timeDone = intent.getIntExtra(EXTRA_TIME_DONE, 0);
            minutes = (intent.getIntExtra(EXTRA_TIME, 0) % 3600000) / 60000;

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.RIGHT_LEFT,
                    new int[]{Color.parseColor(intent.getStringExtra(EXTRA_COLOR)), 0xFF131313});

            gd.setCornerRadius(0f);

        } else {
            getSupportActionBar().setTitle("Add project");
            id = intent.getIntExtra(EXTRA_ID, -1);
        }
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_add_project, R.id.navigation_project_tasks, R.id.navigation_stat)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main2);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (((item.getItemId() == R.id.navigation_project_tasks)
                        || (item.getItemId() == R.id.navigation_stat))
                        && (id == -1)) {
                    Toast.makeText(MainActivity2.this, "Save project first", Toast.LENGTH_SHORT).show();
                    return false;
                }
                navController.navigate(item.getItemId());
                return true;
            }
        });

        MainActivity.setBackClick(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_project_menu, menu);
        return true;
    }


}

