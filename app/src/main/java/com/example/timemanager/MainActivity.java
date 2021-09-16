package com.example.timemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    public static RecyclerView recycleView;
    String project[], time[], color[];
    FloatingActionButton add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycleView = (RecyclerView) findViewById(R.id.recyclerview);

        project = getResources().getStringArray(R.array.Project);
        time = getResources().getStringArray(R.array.Time);
        color = getResources().getStringArray(R.array.Color);

        Project_Adapter projectAdapter = new Project_Adapter(this, project, time, color);
        recycleView.setAdapter(projectAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));


        add_button = findViewById(R.id.add_item);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Add_item_Activity.class);
                startActivity(intent);
            }
        });
    }
}