package com.example.timemanager.ui.projects;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timemanager.MainActivity;
import com.example.timemanager.MainActivity2;
import com.example.timemanager.ProjectAdapter;
import com.example.timemanager.R;
import com.example.timemanager.databinding.FragmentProjectsBinding;
import com.example.timemanager.entity.Project;
import com.example.timemanager.viewmodel.ProjectSessionViewModel;
import com.example.timemanager.viewmodel.ProjectViewModel;
import com.example.timemanager.viewmodel.TaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProjectsFragment extends Fragment {


    private FragmentProjectsBinding binding;
    static ProjectViewModel projectViewModel;
    static TaskViewModel taskViewModel;


    static ProjectSessionViewModel projectSessionViewModel;
    public static SharedPreferences sharedPreferences;
    ProjectAdapter projectAdapter;

    public static ProjectViewModel getProjectViewModel() {
        return projectViewModel;
    }

    public static TaskViewModel getTaskViewModel() {
        return taskViewModel;
    }

    public static ProjectSessionViewModel getProjectSessionViewModel() {
        return projectSessionViewModel;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        binding = FragmentProjectsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sharedPreferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);

        RecyclerView recycleView = (RecyclerView) root.findViewById(R.id.recyclerview);
        recycleView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        projectAdapter = new ProjectAdapter();
        recycleView.setAdapter(projectAdapter);

        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        projectSessionViewModel = new ViewModelProvider(this).get(ProjectSessionViewModel.class);


        if (sharedPreferences.getBoolean("showAllProjects", false) == true) {
            showAllProject();
        } else {
            showDayProject();
        }


        FloatingActionButton buttonAddProject = root.findViewById(R.id.add_task);
        buttonAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity2.class);
                intent.putExtra(MainActivity2.EXTRA_ID, -1);
                startActivity(intent);
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
                taskViewModel.deleteProjectTasks(projectAdapter.getProjectAt(viewHolder.getAdapterPosition()).getTitle());
                Toast.makeText(getActivity(), "Project deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recycleView);

        projectAdapter.setOnItemClickListener(new ProjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Project project) {
                Intent intent = new Intent(getActivity(), MainActivity2.class);
                intent.putExtra(MainActivity2.EXTRA_ID, project.getId());
                intent.putExtra(MainActivity2.EXTRA_TITLE, project.getTitle());
                intent.putExtra(MainActivity2.EXTRA_COLOR, project.getColor());
                intent.putExtra(MainActivity2.EXTRA_TIME_DONE, project.getTimeDone());
                intent.putExtra(MainActivity2.EXTRA_TIME, project.getTime());
                intent.putExtra(MainActivity2.EXTRA_DAYS, project.getDays());
                startActivity(intent);

            }
        });


        return root;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.projects_opttion_menu, menu);
        menu.findItem(R.id.show_done).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        if (sharedPreferences.getBoolean("showAllProjects", false) == true) {
            menu.findItem(R.id.show_all).setTitle("Show day projects");
        } else {
            menu.findItem(R.id.show_all).setTitle("Show all");
        }

        super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_all:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (sharedPreferences.getBoolean("showAllProjects", false) == true) {

                    showDayProject();
                    editor.putBoolean("showAllProjects", false);

                    Toast.makeText(getActivity(), "Only day projects", Toast.LENGTH_SHORT).show();
                } else {
                    showAllProject();
                    editor.putBoolean("showAllProjects", true);

                    Toast.makeText(getActivity(), "All projects", Toast.LENGTH_SHORT).show();
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

    private void showAllProject() {
        projectViewModel.getAllProject().observe(getViewLifecycleOwner(), projects -> projectAdapter.submitList(projects));
    }

    private void showDayProject() {
        projectViewModel.getDayProject("%" + new SimpleDateFormat("EEE", Locale.ENGLISH).format(new Date()) + "%").observe(getViewLifecycleOwner(), new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                projectAdapter.submitList(projects);
            }
        });
    }
}