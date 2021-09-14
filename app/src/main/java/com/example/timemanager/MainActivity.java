package com.example.timemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public static RecyclerView recycleView;
    String project[], time[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycleView = (RecyclerView) findViewById(R.id.recyclerview);

        project = getResources().getStringArray(R.array.Project);
        time = getResources().getStringArray(R.array.Time);

        Project_Adapter projectAdapter = new Project_Adapter(this, project,time);
        recycleView.setAdapter(projectAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
    }
}