package com.example.timemanager.ui.projecttask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timemanager.MainActivity;
import com.example.timemanager.MainActivity2;
import com.example.timemanager.R;
import com.example.timemanager.TaskAdapter;
import com.example.timemanager.databinding.FragmentProjectTasksBinding;
import com.example.timemanager.entity.Task;
import com.example.timemanager.ui.addproject.AddEditProjectFragment;
import com.example.timemanager.viewmodel.TaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProjectTasksFragment extends Fragment {

    public static TaskViewModel taskViewModel;
    FragmentProjectTasksBinding binding;
    View addTaskPopupView;
    int width;
    int height;
    private int selectedTaskId = -1;
    PopupWindow popupWindow;
    EditText editTextTaskTitle;
    ImageView imageView;
    TextView textView;
    TaskAdapter taskAdapter;
    LiveData<List<Task>> allTasks, notDoneTasks;
    public static SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        sharedPreferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        binding = FragmentProjectTasksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        addTaskPopupView = LayoutInflater.from(getActivity()).inflate(R.layout.add_task_popup, null);
        imageView = addTaskPopupView.findViewById(R.id.imageView);
        textView = addTaskPopupView.findViewById(R.id.textView);
        editTextTaskTitle = addTaskPopupView.findViewById(R.id.editText);

        width = LinearLayout.LayoutParams.WRAP_CONTENT;
        height = LinearLayout.LayoutParams.WRAP_CONTENT;
        RecyclerView recycleView = (RecyclerView) root.findViewById(R.id.recyclerview);
        recycleView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        taskAdapter = new TaskAdapter();
        recycleView.setAdapter(taskAdapter);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
       showTask();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                taskViewModel.delete(taskAdapter.getTaskAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Task deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recycleView);

        taskAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task, View view) {
                editTextTaskTitle.setText(task.getTitle());
                selectedTaskId = task.getId();
                showPopup(view);

            }
        });


        root.findViewById(R.id.add_task2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedTaskId = -1;
                EditText editText = addTaskPopupView.findViewById(R.id.editText);
                editText.setText("");
                showPopup(view);
            }
        });

        addTaskPopupView.findViewById(R.id.save_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = addTaskPopupView.findViewById(R.id.editText);
                if (editText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "Please insert task title", Toast.LENGTH_SHORT).show();
                    return;
                }
                Task task = new Task(editText.getText().toString(), MainActivity2.getId(), false, MainActivity2.getProjectTitle(), MainActivity2.getColor());
                if (selectedTaskId == -1) {
                    taskViewModel.insert(task);
                } else {
                    task.setId(selectedTaskId);
                    taskViewModel.update(task);
                }
                popupWindow.dismiss();
            }
        });

        return root;
    }

    private void showPopup(View view) {
        imageView.setColorFilter(Color.parseColor(AddEditProjectFragment.color));
        textView.setText(AddEditProjectFragment.title);
        popupWindow = new PopupWindow(addTaskPopupView, width, height, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        View container = popupWindow.getContentView().getRootView();
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.5f;
        wm.updateViewLayout(container, p);
    }

    private void saveProject() {
        Toast.makeText(getActivity(), "Project saved", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.projects_opttion_menu, menu);
        menu.findItem(R.id.show_all).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        if (sharedPreferences.getBoolean("showDoneTask", false)) {
            menu.findItem(R.id.show_done).setTitle("Show only not done task");
        } else {
            menu.findItem(R.id.show_done).setTitle("Show all");
        }

        super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_project:
                saveProject();

            case R.id.show_done:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (sharedPreferences.getBoolean("showDoneTask", false)) {
                    editor.putBoolean("showDoneTask", false);
                    allTasks.removeObservers(getViewLifecycleOwner());
                    Toast.makeText(getActivity(), "Only not done task", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putBoolean("showDoneTask", true);
                    notDoneTasks.removeObservers(getViewLifecycleOwner());
                    Toast.makeText(getActivity(), "All task", Toast.LENGTH_SHORT).show();
                }
                editor.apply();
                showTask();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showTask() {
        if (sharedPreferences.getBoolean("showDoneTask", false)) {
            allTasks = taskViewModel.getAllProjectTasks(String.valueOf(MainActivity2.getId()));
            allTasks.observe(getViewLifecycleOwner(), tasks -> taskAdapter.submitList(tasks));

        } else {
            notDoneTasks = taskViewModel.getNotDoneProjectTasks(String.valueOf(MainActivity2.getId()));
            notDoneTasks.observe(getViewLifecycleOwner(), tasks -> taskAdapter.submitList(tasks));
        }

    }
    private void showNotDoneTask() {
        taskViewModel.getNotDoneProjectTasks(String.valueOf(MainActivity2.getId())).observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                taskAdapter.submitList(tasks);
            }
        });
    }

    private void showAllTask() {

        taskViewModel.getAllProjectTasks(String.valueOf(MainActivity2.getId())).observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                taskAdapter.submitList(tasks);
            }
        });
    }
}