package com.example.timemanager.ui.alltasks;

import android.os.Bundle;
import android.view.LayoutInflater;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAllTasksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recycleView = (RecyclerView) root.findViewById(R.id.recyclerview);
        recycleView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        TaskAdapter taskAdapter = new TaskAdapter();
        recycleView.setAdapter(taskAdapter);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getAllTask().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                taskAdapter.submitList(tasks);
            }
        });

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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}