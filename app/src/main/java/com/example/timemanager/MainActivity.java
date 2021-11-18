package com.example.timemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static ProjectViewModel projectViewModel;
    static int startedPosition = -1;
    static int startedWorker = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onNewIntent(getIntent());


        RecyclerView recycleView = (RecyclerView) findViewById(R.id.recyclerview);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        ProjectAdapter projectAdapter = new ProjectAdapter();
        recycleView.setAdapter(projectAdapter);

        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);
        projectViewModel.getAllProject().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                projectAdapter.submitList(projects);
            }
        });


        FloatingActionButton buttonAddProject = findViewById(R.id.add_item);
        buttonAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStartForResult.launch(new Intent(MainActivity.this, AddProjectActivity.class));
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                projectViewModel.delete(projectAdapter.getProjectAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Project deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recycleView);
    }

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        String title = intent.getStringExtra(AddProjectActivity.EXTRA_TITLE);
                        String color = intent.getStringExtra(AddProjectActivity.EXTRA_COLOR);
                        int timePerDay = intent.getIntExtra(AddProjectActivity.EXTRA_TIME, 0);

                        Project project = new Project(title, timePerDay, color);
                        projectViewModel.insert(project);

                        Toast.makeText(MainActivity.this, "Project saved", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("startedPosition")) {

                // extract the extra-data in the Notification
                startedPosition = extras.getInt("startedPosition",-1);
                startedWorker = extras.getInt("startedWorker",0);
            }
        }

    }
}