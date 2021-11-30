package com.example.timemanager.ui.projects;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timemanager.ProjectAdapter;
import com.example.timemanager.R;
import com.example.timemanager.databinding.FragmentProjectsBinding;
import com.example.timemanager.entity.Project;
import com.example.timemanager.ui2.project.AddProjectActivity;
import com.example.timemanager.viewmodel.ProjectViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProjectsFragment extends Fragment {


    private FragmentProjectsBinding binding;
    static ProjectViewModel projectViewModel;



    public static ProjectViewModel getProjectViewModel() {
        return projectViewModel;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentProjectsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        RecyclerView recycleView = (RecyclerView) root.findViewById(R.id.recyclerview);
        recycleView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        ProjectAdapter projectAdapter = new ProjectAdapter();
        recycleView.setAdapter(projectAdapter);

        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);
        projectViewModel.getAllProject().observe(getViewLifecycleOwner(), new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                projectAdapter.submitList(projects);
            }
        });


        FloatingActionButton buttonAddProject = root.findViewById(R.id.add_task);
        buttonAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProjectStartForResult.launch(new Intent(getActivity(), AddProjectActivity.class));
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
                Intent intent = new Intent(getActivity(), AddProjectActivity.class);
                intent.putExtra(AddProjectActivity.EXTRA_ID, project.getId());
                intent.putExtra(AddProjectActivity.EXTRA_TITLE, project.getTitle());
                intent.putExtra(AddProjectActivity.EXTRA_COLOR, project.getColor());
                intent.putExtra(AddProjectActivity.EXTRA_TIME, project.getTime());
                editProjectStartForResult.launch( intent);

            }
        });

        return root;
    }
    ActivityResultLauncher<Intent> addProjectStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        String title = intent.getStringExtra(AddProjectActivity.EXTRA_TITLE);
                        String color = intent.getStringExtra(AddProjectActivity.EXTRA_COLOR);
                        int timePerDay = intent.getIntExtra(AddProjectActivity.EXTRA_TIME, 0);

                        Project project = new Project(title, timePerDay, color);
                        projectViewModel.insert(project);

                        Toast.makeText(getActivity(), "Project saved", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    ActivityResultLauncher<Intent> editProjectStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent.getIntExtra(AddProjectActivity.EXTRA_ID,-1)==-1){
                            Toast.makeText(getActivity(), "Project can't be updated", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String title = intent.getStringExtra(AddProjectActivity.EXTRA_TITLE);
                        String color = intent.getStringExtra(AddProjectActivity.EXTRA_COLOR);
                        int timePerDay = intent.getIntExtra(AddProjectActivity.EXTRA_TIME, 0);

                        Project project = new Project(title, timePerDay, color);
                        project.setId(intent.getIntExtra(AddProjectActivity.EXTRA_ID,-1));
                        projectViewModel.update(project);

                        Toast.makeText(getActivity(), "Project saved", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}