package com.example.timemanager.ui.alltasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timemanager.MainActivity;
import com.example.timemanager.MainActivity2;
import com.example.timemanager.R;
import com.example.timemanager.TaskAdapter;
import com.example.timemanager.databinding.FragmentAllTasksBinding;
import com.example.timemanager.entity.Task;
import com.example.timemanager.viewmodel.TaskViewModel;

import java.util.List;

public class AllTasksFragment extends Fragment {


    private FragmentAllTasksBinding binding;
    public static TaskViewModel taskViewModel;
    public static SharedPreferences sharedPreferences;
    RecyclerView recycleView;
    TaskAdapter taskAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = FragmentAllTasksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

         recycleView = (RecyclerView) root.findViewById(R.id.recyclerview);
        recycleView.setLayoutManager(new LinearLayoutManager(root.getContext()));
         taskAdapter = new TaskAdapter();
        recycleView.setAdapter(taskAdapter);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        sharedPreferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("showDoneTask", false) == true) {
            showAllTask();
        } else {
            showNotDoneTask();
        }



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

        return root;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.projects_opttion_menu, menu);
        menu.findItem(R.id.show_all).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        if (sharedPreferences.getBoolean("showDoneTask", false) == true) {
            menu.findItem(R.id.show_done).setTitle("Show only not done task");
        } else {
            menu.findItem(R.id.show_done).setTitle("Show all");
        }

        super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_done:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (sharedPreferences.getBoolean("showDoneTask", false) == true) {
                    showNotDoneTask();
                    editor.putBoolean("showDoneTask", false);
                    Toast.makeText(getActivity(), "Only not done task", Toast.LENGTH_SHORT).show();
                } else {
                    showAllTask();
                    editor.putBoolean("showDoneTask", true);
                    Toast.makeText(getActivity(), "All task", Toast.LENGTH_SHORT).show();
                }
                editor.apply();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showNotDoneTask(){
        taskViewModel.getNotDoneTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                taskAdapter.submitList(tasks);
            }
        });
    }

    private void showAllTask(){
        taskViewModel.getAllTask().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                taskAdapter.submitList(tasks);
            }
        });
    }
}