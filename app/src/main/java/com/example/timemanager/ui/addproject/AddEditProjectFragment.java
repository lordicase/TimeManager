package com.example.timemanager.ui.addproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemanager.MainActivity;
import com.example.timemanager.MainActivity2;
import com.example.timemanager.R;
import com.example.timemanager.databinding.FragmentAddProjectBinding;
import com.example.timemanager.entity.Project;
import com.example.timemanager.viewmodel.ProjectViewModel;
import com.example.timemanager.viewmodel.TaskViewModel;

public class AddEditProjectFragment extends Fragment implements View.OnClickListener {


    int width;
    int height;
    static int projectColor = -769226;

    private PopupWindow popupWindow;
    private EditText editTextProjectTitle;
    private ImageView imageView34, imageView21;
    private NumberPicker hourPicker, minutesPicker;
    View root;
    public static String color = "#F44336";
    public static String title;
    int id = -1;
    ProjectViewModel projectViewModel;
    TaskViewModel taskViewModel;

    private FragmentAddProjectBinding binding;

    Button monButton, tueButton, wedButton, thuButton, friButton, satButton, sunButton;
    String days;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);


        binding = FragmentAddProjectBinding.inflate(inflater, container, false);
        root = binding.getRoot();


        hourPicker = root.findViewById(R.id.hours_picker);
        minutesPicker = root.findViewById(R.id.minutes_picker);

        editTextProjectTitle = root.findViewById(R.id.editText);
        imageView34 = root.findViewById(R.id.imageView34);
        imageView21 = root.findViewById(R.id.imageView21);

        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(24);
        minutesPicker.setMinValue(0);
        minutesPicker.setMaxValue(60);
        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                if (hourPicker.getValue() == 24) {
                    minutesPicker.setMaxValue(0);
                } else {
                    minutesPicker.setMaxValue(60);
                }
            }
        });


        //days button
        monButton = root.findViewById(R.id.monButton);
        tueButton = root.findViewById(R.id.tueButton);
        wedButton = root.findViewById(R.id.wedButton);
        thuButton = root.findViewById(R.id.thuButton);
        friButton = root.findViewById(R.id.friButton);
        satButton = root.findViewById(R.id.satButton);
        sunButton = root.findViewById(R.id.sunButton);

        monButton.setOnClickListener(this);
        tueButton.setOnClickListener(this);
        wedButton.setOnClickListener(this);
        thuButton.setOnClickListener(this);
        friButton.setOnClickListener(this);
        satButton.setOnClickListener(this);
        sunButton.setOnClickListener(this);
        //COLOR CHANGING
        // inflate the layout of the popup window
        View selectColorPopupView = LayoutInflater.from(getActivity()).inflate(R.layout.select_color_popup, null);

        // create the popup window
        width = LinearLayout.LayoutParams.WRAP_CONTENT;
        height = LinearLayout.LayoutParams.WRAP_CONTENT;

        selectColorPopupView.findViewById(R.id.imageView21).setOnClickListener(this);
        selectColorPopupView.findViewById(R.id.imageView22).setOnClickListener(this);
        selectColorPopupView.findViewById(R.id.imageView23).setOnClickListener(this);
        selectColorPopupView.findViewById(R.id.imageView24).setOnClickListener(this);
        selectColorPopupView.findViewById(R.id.imageView26).setOnClickListener(this);
        selectColorPopupView.findViewById(R.id.imageView27).setOnClickListener(this);
        selectColorPopupView.findViewById(R.id.imageView28).setOnClickListener(this);
        selectColorPopupView.findViewById(R.id.imageView29).setOnClickListener(this);
        selectColorPopupView.findViewById(R.id.imageView30).setOnClickListener(this);
        selectColorPopupView.findViewById(R.id.imageView31).setOnClickListener(this);
        selectColorPopupView.findViewById(R.id.imageView32).setOnClickListener(this);
        selectColorPopupView.findViewById(R.id.imageView33).setOnClickListener(this);

        root.findViewById(R.id.imageView21).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupWindow = new PopupWindow(selectColorPopupView, width, height, true);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                View container = popupWindow.getContentView().getRootView();
                Context context = popupWindow.getContentView().getContext();
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
                p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                p.dimAmount = 0.5f;
                wm.updateViewLayout(container, p);
            }
        });


        if (MainActivity2.getId() != -1) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Edit project");
            id = MainActivity2.getId();
            title = MainActivity2.getProjectTitle();
            color = MainActivity2.getColor();
            days = MainActivity2.getDays();
            editTextProjectTitle.setText(title);
            hourPicker.setValue(MainActivity2.getHour());
            minutesPicker.setValue(MainActivity2.getMinutes());

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.RIGHT_LEFT,
                    new int[]{Color.parseColor(MainActivity2.getColor()), 0xFF131313});

            gd.setCornerRadius(0f);
            root.findViewById(R.id.constraintLayout7).setBackground(gd);


            imageView34.setColorFilter(Color.parseColor(color));
            imageView21.setColorFilter(Color.parseColor(color));

            if (days.contains("MON")) {
                monButton.setActivated(true);
            }
            if (days.contains("TUE")) {
                tueButton.setActivated(true);
            }
            if (days.contains("WED")) {
                wedButton.setActivated(true);
            }
            if (days.contains("THU")) {
                thuButton.setActivated(true);
            }
            if (days.contains("FRI")) {
                friButton.setActivated(true);
            }
            if (days.contains("SAT")) {
                satButton.setActivated(true);
            }
            if (days.contains("SUN")) {
                sunButton.setActivated(true);
            }


        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add project");
            monButton.setActivated(true);
            tueButton.setActivated(true);
            wedButton.setActivated(true);
            thuButton.setActivated(true);
            friButton.setActivated(true);
            satButton.setActivated(true);
            sunButton.setActivated(true);

        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //on chose color dot click
    @Override
    public void onClick(View view) {
        if ((view.getId() == R.id.monButton) ||
                (view.getId() == R.id.tueButton) ||
                (view.getId() == R.id.wedButton) ||
                (view.getId() == R.id.thuButton) ||
                (view.getId() == R.id.friButton) ||
                (view.getId() == R.id.satButton) ||
                (view.getId() == R.id.sunButton)) {
            if (view.isActivated()) {
                view.setActivated(false);
            } else {
                view.setActivated(true);
            }
        } else {
            projectColor = ImageViewCompat.getImageTintList((ImageView) view).getDefaultColor();
            color = String.format("#%06X", (0xFFFFFF & projectColor));
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.RIGHT_LEFT,
                    new int[]{projectColor, 0xFF131313});

            gd.setCornerRadius(0f);
            root.findViewById(R.id.constraintLayout7).setBackground(gd);

            imageView34.setColorFilter(projectColor);
            imageView21.setColorFilter(projectColor);
            popupWindow.dismiss();
        }
    }


    private void saveProject() {

        String title = editTextProjectTitle.getText().toString();
        String newDays = "";
        int timePerDay = (hourPicker.getValue() * 3600000 + minutesPicker.getValue() * 60000);

        if (title.trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please insert project title", Toast.LENGTH_SHORT).show();
            return;
        }

        if (monButton.isActivated()) {
            newDays += "MON";
        }
        if (tueButton.isActivated()) {
            newDays += "TUE";
        }
        if (wedButton.isActivated()) {
            newDays += "WED";
        }
        if (thuButton.isActivated()) {
            newDays += "THU";
        }
        if (friButton.isActivated()) {
            newDays += "FRI";
        }
        if (satButton.isActivated()) {
            newDays += "SAT";
        }
        if (sunButton.isActivated()) {
            newDays += "SUN";
        }

        Project project = new Project(title, timePerDay, color);
        project.setDays(newDays);
        if (id != -1) {
            project.setId(id);
            project.setTimeDone(MainActivity2.getTimeDone());
            projectViewModel.update(project);
            taskViewModel.updateTaskProjectTitle(MainActivity2.getId(),title,color);
        } else {
            projectViewModel.insert(project);
        }
        Toast.makeText(getActivity(), "Project saved", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), MainActivity.class));


    }


    @Override
    public void onResume() {
        super.onResume();
        if (MainActivity2.getId() != -1) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Edit project");
            id = MainActivity2.getId();
            color = MainActivity2.getColor();
            editTextProjectTitle.setText(MainActivity2.getProjectTitle());
            hourPicker.setValue(MainActivity2.getHour());
            minutesPicker.setValue(MainActivity2.getMinutes());

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.RIGHT_LEFT,
                    new int[]{Color.parseColor(MainActivity2.getColor()), 0xFF131313});

            gd.setCornerRadius(0f);
            root.findViewById(R.id.constraintLayout7).setBackground(gd);


            imageView34.setColorFilter(Color.parseColor(color));
            imageView21.setColorFilter(Color.parseColor(color));

        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add project");
        }
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


}