package com.example.timemanager.ui.projectstat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemanager.MainActivity;
import com.example.timemanager.R;
import com.example.timemanager.dao.ProjectSessionDao;
import com.example.timemanager.databinding.FragmentProjectStatBinding;
import com.example.timemanager.entity.ProjectSession;
import com.example.timemanager.viewmodel.ProjectSessionViewModel;

import java.util.List;

public class ProjectStatFragment extends Fragment {


    private FragmentProjectStatBinding binding;
    ProjectSessionViewModel projectSessionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = FragmentProjectStatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    private void saveProject() {
        Toast.makeText(getActivity(), "Project saved", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_project:
                saveProject();
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