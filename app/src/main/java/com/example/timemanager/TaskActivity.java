package com.example.timemanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timemanager.entity.Task;
import com.example.timemanager.viewmodel.TaskViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class TaskActivity extends AppCompatActivity {

    public static TaskViewModel taskViewModel;
    int width;
    int height;
    private View addTaskPopupView;
    private EditText editTextTaskTitle;
    private ImageView imageView;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        addTaskPopupView = inflater.inflate(R.layout.add_task_popup, null);

        // create the popup window
        width = LinearLayout.LayoutParams.WRAP_CONTENT;
        height = LinearLayout.LayoutParams.WRAP_CONTENT;

        RecyclerView recycleView = (RecyclerView) findViewById(R.id.recyclerview);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        TaskAdapter taskAdapter = new TaskAdapter();
        recycleView.setAdapter(taskAdapter);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigatin_view);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.project:
                        Intent intentProject = new Intent(TaskActivity.this,AddProjectActivity.class);
                        startActivity(intentProject);
                    case R.id.task:
                        Intent intentTask = new Intent(TaskActivity.this,TaskActivity.class);
                        startActivity(intentTask);
                    default:
                        return true;
                }
            }
        });
//        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
//        taskViewModel.getAllTask().observe(this, new Observer<List<Task>>() {
//            @Override
//            public void onChanged(List<Task> tasks) {
//                taskAdapter.submitList(tasks);
//            }
//        });
//
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                taskViewModel.delete(taskAdapter.getTaskAt(viewHolder.getAdapterPosition()));
//                Toast.makeText(TaskActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
//            }
//        }).attachToRecyclerView(recycleView);
//
//        taskAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Task task, View view) {
////                    editTextTaskTitle = addTaskPopupView.findViewById(R.id.editText);
////                    editTextTaskTitle.setText(task.getTitle());
////                    showTaskPopup(intent,view);
//            }
//        });
//
//        addTaskPopupView.findViewById(R.id.save_task).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditText editText = addTaskPopupView.findViewById(R.id.editText);
//                if (editText.getText().toString().trim().isEmpty()) {
//                    Toast.makeText(TaskActivity.this, "Please insert task title", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                Task task = new Task(editText.getText().toString(),id,false);
//                taskViewModel.insert(task);
//                popupWindow.dismiss();
//            }
//        });
//
//        FloatingActionButton buttonAddTask = findViewById(R.id.add_task);
//        buttonAddTask.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                imageView = addTaskPopupView.findViewById(R.id.imageView);
//                textView = addTaskPopupView.findViewById(R.id.textView);
//                imageView.setColorFilter(Color.parseColor(color));
//                textView.setText(intent.getStringExtra(EXTRA_TITLE));
//                popupWindow = new PopupWindow(addTaskPopupView, width, height, true);
//                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//
//                View container = popupWindow.getContentView().getRootView();
//                Context context = popupWindow.getContentView().getContext();
//                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//                WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
//                p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//                p.dimAmount = 0.5f;
//                wm.updateViewLayout(container, p);
//            }
//        });
    }
}
