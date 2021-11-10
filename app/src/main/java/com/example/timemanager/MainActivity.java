package com.example.timemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProjectViewModel projectViewModel;
    public static RecyclerView recycleView;
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

        add_button = findViewById(R.id.add_item);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddProjectActivity.class);
                startActivity(intent);
            }
        });
    }
}