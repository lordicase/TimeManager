package com.example.timemanager.ui.allstat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemanager.R;
import com.example.timemanager.databinding.FragmentAllStatBinding;
import com.example.timemanager.entity.ProjectSession;
import com.example.timemanager.viewmodel.ProjectSessionViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AllStatFragment extends Fragment {


    ProjectSessionViewModel projectSessionViewModel;
    private FragmentAllStatBinding binding;
    PieChart pieChart;
    Calendar dateNow;
    Calendar startOfRange, endOfRange;
    Button dayButton, weekButton, monthButton, yearButton;
    ImageButton previousButton, nextButton, skipButton;
    TextView dateTextView;
    SimpleDateFormat simpleDateFormat;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAllStatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        dayButton = root.findViewById(R.id.button);
        weekButton = root.findViewById(R.id.button2);
        monthButton = root.findViewById(R.id.button3);
        yearButton = root.findViewById(R.id.button4);
        previousButton = root.findViewById(R.id.imageButton2);
        nextButton = root.findViewById(R.id.imageButton3);
        skipButton = root.findViewById(R.id.imageButton4);
        dateTextView = root.findViewById(R.id.textView7);
        dayButton.setActivated(true);
        weekButton.setActivated(false);
        monthButton.setActivated(false);
        yearButton.setActivated(false);
        dateNow = Calendar.getInstance();
        changeDate();

        pieChart = (PieChart) root.findViewById(R.id.chart);
        setChart();

        dayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayButton.setActivated(true);
                weekButton.setActivated(false);
                monthButton.setActivated(false);
                yearButton.setActivated(false);
                changeDate();
            }
        });

        weekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayButton.setActivated(false);
                weekButton.setActivated(true);
                monthButton.setActivated(false);
                yearButton.setActivated(false);
                changeDate();
            }
        });

        monthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayButton.setActivated(false);
                weekButton.setActivated(false);
                monthButton.setActivated(true);
                yearButton.setActivated(false);
                changeDate();
            }
        });

        yearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayButton.setActivated(false);
                weekButton.setActivated(false);
                monthButton.setActivated(false);
                yearButton.setActivated(true);
                changeDate();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dayButton.isActivated()) {
                    dateNow.add(Calendar.DAY_OF_WEEK, -1);
                } else if (weekButton.isActivated()) {
                    dateNow.add(Calendar.DAY_OF_MONTH, -7);
                } else if (monthButton.isActivated()) {
                    dateNow.add(Calendar.MONTH, -1);
                } else {
                    dateNow.add(Calendar.YEAR, -1);
                }
                changeDate();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dateNow.equals(new Date())) {
                    if (dayButton.isActivated()) {
                        dateNow.add(Calendar.DAY_OF_WEEK, 1);
                    } else if (weekButton.isActivated()) {
                        dateNow.add(Calendar.DAY_OF_MONTH, 7);
                    } else if (monthButton.isActivated()) {
                        dateNow.add(Calendar.MONTH, 1);
                    } else {
                        dateNow.add(Calendar.YEAR, 1);
                    }
                } else {
                    return;
                }
                changeDate();
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateNow = Calendar.getInstance();
                changeDate();
            }
        });
        return root;
    }

    private void changeDate() {
        startOfRange = Calendar.getInstance();
        endOfRange = Calendar.getInstance();
        startOfRange.setTime(dateNow.getTime());
        startOfRange.set(Calendar.HOUR_OF_DAY, 0);
        startOfRange.set(Calendar.MINUTE, 0);
        endOfRange.setTime(dateNow.getTime());
        endOfRange.set(Calendar.HOUR_OF_DAY, 23);
        endOfRange.set(Calendar.MINUTE, 59);
        if (dayButton.isActivated()) {
            simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
            dateTextView.setText(simpleDateFormat.format(startOfRange.getTime()));

        } else if (weekButton.isActivated()) {


            startOfRange.set(Calendar.DAY_OF_WEEK, 2);
            endOfRange.set(Calendar.DAY_OF_WEEK, 1);
            simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
            dateTextView.setText(simpleDateFormat.format(startOfRange.getTime()) + " - " + simpleDateFormat.format(endOfRange.getTime()));
        } else if (monthButton.isActivated()) {


            startOfRange.set(Calendar.DAY_OF_MONTH, 1);

            endOfRange.set(Calendar.DAY_OF_MONTH, dateNow.getMaximum(Calendar.DAY_OF_MONTH));
            simpleDateFormat = new SimpleDateFormat("MMMM",Locale.ENGLISH);
            dateTextView.setText(simpleDateFormat.format(startOfRange.getTime()));
        } else {

            startOfRange.set(Calendar.DAY_OF_YEAR, 1);

            endOfRange.set(Calendar.DAY_OF_YEAR, dateNow.getMaximum(Calendar.DAY_OF_YEAR));
            simpleDateFormat = new SimpleDateFormat("y",Locale.ENGLISH);
            dateTextView.setText(simpleDateFormat.format(startOfRange.getTime()));
        }
        setChart();
    }


    private String getTime(double timeDone) {
        int time = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            time = Math.toIntExact(Math.round(timeDone / 1000));
        }
        String timeS;
        if (time >= 86400) {
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

    void setChart() {
        projectSessionViewModel = new ViewModelProvider(this).get(ProjectSessionViewModel.class);
        projectSessionViewModel.getAllProjectSession(startOfRange.getTimeInMillis(), endOfRange.getTimeInMillis()).observe(getViewLifecycleOwner(), new Observer<List<ProjectSession>>() {
            @Override
            public void onChanged(List<ProjectSession> projectSessions) {
                if (projectSessions.isEmpty()) {
                    pieChart.setData(null);
                    pieChart.invalidate();
                    return;
                }
                double timeDone = 0;
                ArrayList<PieEntry> entries = new ArrayList<>();
                String currentTitle = projectSessions.get(0).getProjectTitle();

                for (ProjectSession projectSession : projectSessions) {
                    if (currentTitle.equals(projectSession.getProjectTitle())) {
                        timeDone += projectSession.getEndTime() - projectSession.getStartTime();
                    } else {

                        entries.add(new PieEntry(Math.round(timeDone / 1000), currentTitle + "\n" + getTime(timeDone)));
                        currentTitle = projectSession.getProjectTitle();
                        timeDone = projectSession.getEndTime() - projectSession.getStartTime();
                    }
                }
                entries.add(new PieEntry(Math.round(timeDone / 1000), currentTitle + "\n" + getTime(timeDone)));
                ArrayList<Integer> colors = new ArrayList<>();
                for (int color : ColorTemplate.MATERIAL_COLORS) {
                    colors.add(color);
                }

                for (int color : ColorTemplate.VORDIPLOM_COLORS) {
                    colors.add(color);
                }

                PieDataSet dataSet = new PieDataSet(entries, "");
                dataSet.setColors(colors);


                PieData data = new PieData(dataSet);

                data.setDrawValues(false);
                data.setValueFormatter(new PercentFormatter(pieChart));
                data.setValueTextSize(12f);
                data.setValueTextColor(Color.BLACK);

                pieChart.setData(data);
                pieChart.getDescription().setEnabled(false);
                pieChart.invalidate();
            }
        });
    }
}