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
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static ProjectViewModel projectViewModel;
    private static RecyclerView recycleView;
    String project[], time[], color[];
    FloatingActionButton add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        project = getResources().getStringArray(R.array.Project);
        time = getResources().getStringArray(R.array.Time);
        color = getResources().getStringArray(R.array.Color);

        recycleView = (RecyclerView) findViewById(R.id.recyclerview);
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

                        Project project = new Project(title,timePerDay,color);
                        projectViewModel.insert(project);

                        Toast.makeText(MainActivity.this, "Project saved", Toast.LENGTH_SHORT).show();
                    }
                }
            });

}