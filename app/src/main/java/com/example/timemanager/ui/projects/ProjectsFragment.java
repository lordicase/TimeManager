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
import com.example.timemanager.viewmodel.ProjectViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProjectsFragment extends Fragment {


    private FragmentProjectsBinding binding;
    static ProjectViewModel projectViewModel;
    public static SharedPreferences sharedPreferences;
    public static Boolean showAll = true;
    public static ProjectViewModel getProjectViewModel() {
        return projectViewModel;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        binding = FragmentProjectsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sharedPreferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);

        RecyclerView recycleView = (RecyclerView) root.findViewById(R.id.recyclerview);
        recycleView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        ProjectAdapter projectAdapter = new ProjectAdapter();
        recycleView.setAdapter(projectAdapter);

        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);


        if (showAll) {
            projectViewModel.getAllProject().observe(getViewLifecycleOwner(), new Observer<List<Project>>() {
                @Override
                public void onChanged(List<Project> projects) {
                    projectAdapter.submitList(projects);
                }
            });
        } else {

            projectViewModel.getDayProject("%"+new SimpleDateFormat("EEE", Locale.ENGLISH).format(new Date())+"%").observe(getViewLifecycleOwner(), new Observer<List<Project>>() {
                @Override
                public void onChanged(List<Project> projects) {
                    projectAdapter.submitList(projects);
                }
            });
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
                intent.putExtra(MainActivity2.EXTRA_TIME, project.getTime());
                intent.putExtra(MainActivity2.EXTRA_DAYS, project.getDays());
                startActivity(intent);

            }
        });


        if (sharedPreferences.getBoolean("showAllProjects", false) == true) {
            showAll = true;
        } else {
            showAll = false;
        }


        return root;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.projects_opttion_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        if (sharedPreferences.getBoolean("showAllProjects", false) == true) {
            menu.findItem(R.id.show_all).setTitle("Show day projects");
        }else {
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
                    item.setTitle("Show day projects");
                    editor.putBoolean("showAllProjects", false);
                    showAll = false;
                    Toast.makeText(getActivity(), "Only day projects", Toast.LENGTH_SHORT).show();
                } else {
                    item.setTitle("Show all");
                    editor.putBoolean("showAllProjects", true);
                    showAll = true;
                    Toast.makeText(getActivity(), "All projects", Toast.LENGTH_SHORT).show();
                }
                editor.apply();

                return true;
            case R.id.show_done:

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}