package com.example.timemanager.ui.allstat;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timemanager.R;
import com.example.timemanager.databinding.FragmentAllStatBinding;

import com.example.timemanager.entity.Project;
import com.example.timemanager.entity.ProjectSession;
import com.example.timemanager.viewmodel.ProjectSessionViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class AllStatFragment extends Fragment {


    ProjectSessionViewModel projectSessionViewModel;
    private FragmentAllStatBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAllStatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        projectSessionViewModel = new ViewModelProvider(this).get(ProjectSessionViewModel.class);
        projectSessionViewModel.getAllProjectSession().observe(getViewLifecycleOwner(), new Observer<List<ProjectSession>>() {
            @Override
            public void onChanged(List<ProjectSession> projectSessions) {
                double timeDone = 0;
                ArrayList<PieEntry> entries = new ArrayList<>();
                String currentTitle = projectSessions.get(0).getProjectTitle();

                for (ProjectSession projectSession : projectSessions) {
                    if (currentTitle.equals(projectSession.getProjectTitle())) {
                        timeDone += projectSession.getEndTime() - projectSession.getStartTime();
                    } else {

                        entries.add(new PieEntry(Math.round(timeDone / 1000), currentTitle + "\n"+getTime(timeDone)));
                        currentTitle = projectSession.getProjectTitle();
                        timeDone = projectSession.getEndTime() - projectSession.getStartTime();
                    }
                }
                entries.add(new PieEntry(Math.round(timeDone / 1000), currentTitle+ "\n"+getTime(timeDone)));
                ArrayList<Integer> colors = new ArrayList<>();
                for (int color : ColorTemplate.MATERIAL_COLORS) {
                    colors.add(color);
                }

                for (int color : ColorTemplate.VORDIPLOM_COLORS) {
                    colors.add(color);
                }

                PieDataSet dataSet = new PieDataSet(entries, "Projekty");
                dataSet.setColors(colors);
                PieChart pieChart = (PieChart) root.findViewById(R.id.chart);

                PieData data = new PieData(dataSet);

                data.setDrawValues(false);
                data.setValueFormatter(new PercentFormatter(pieChart));
                data.setValueTextSize(12f);
                data.setValueTextColor(Color.BLACK);

                pieChart.setData(data);

            }
        });


        return root;
    }

    private String getTime(double timeDone) {
        int time = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            time = Math.toIntExact(Math.round(timeDone / 1000));
        }
        String timeS;
        if(time>= 86400){
            timeS = time / 86400 + "d " + time % 86400 / 3600 + "h";
        } else if (time >= 3600) {
            timeS = time / 3600 + "h " + time % 3600 / 60 + "min";
        } else if (time >= 60) {
            timeS = time / 60 + "min " + time % 60 + "s";
        } else {
            timeS = time % 60 + "s";
        }
        return timeS;
    }


}