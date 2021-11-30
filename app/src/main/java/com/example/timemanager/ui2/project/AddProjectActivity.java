package com.example.timemanager.ui2.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;

import com.example.timemanager.R;
import com.example.timemanager.ui2.projectTasks.TaskActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class AddProjectActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_TITLE = "com.example.timemanager.EXTRA_TITLE";
    public static final String EXTRA_COLOR = "com.example.timemanager.EXTRA_COLOR";
    public static final String EXTRA_TIME = "com.example.timemanager.EXTRA_TIME";
    public static final String EXTRA_ID = "com.example.timemanager.EXTRA_ID";

    int width;
    int height;
    static int projectColor = -769226;
    private View selectColorPopupView;
    private PopupWindow popupWindow;
    private EditText editTextProjectTitle;
    private ImageView  imageView34, imageView21;
    private NumberPicker hourPicker, minutesPicker;

    int id;
    public static String color = "#F44336";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_lose);

        hourPicker = findViewById(R.id.hours_picker);
        minutesPicker = findViewById(R.id.minutes_picker);

        editTextProjectTitle = findViewById(R.id.editText);
        imageView34 = findViewById(R.id.imageView34);
        imageView21 = findViewById(R.id.imageView21);

        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(24);
        minutesPicker.setMinValue(0);
        minutesPicker.setMaxValue(60);

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

        //COLOR CHANGING
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        selectColorPopupView = inflater.inflate(R.layout.select_color_popup, null);

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

        findViewById(R.id.imageView21).setOnClickListener(new View.OnClickListener() {
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

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Project");
            id = intent.getIntExtra(EXTRA_ID, -1);
            color = intent.getStringExtra(EXTRA_COLOR);
            editTextProjectTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            hourPicker.setValue(intent.getIntExtra(EXTRA_TIME, 0) / 3600000);
            minutesPicker.setValue((intent.getIntExtra(EXTRA_TIME, 0) % 3600000) / 60000);

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.RIGHT_LEFT,
                    new int[]{Color.parseColor(intent.getStringExtra(EXTRA_COLOR)), 0xFF131313});

            gd.setCornerRadius(0f);
            findViewById(R.id.constraintLayout7).setBackground(gd);


            imageView34.setColorFilter(Color.parseColor(color));
            imageView21.setColorFilter(Color.parseColor(color));





        } else {
            setTitle("Add Project");
        }
//        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigatin_view);
//        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                return false;
//            }
//        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigatin_view);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.save_project:
                        saveProject();
                    case R.id.project:
                        Intent intentProject = new Intent(AddProjectActivity.this,AddProjectActivity.class);
                        startActivity(intentProject);
                    case R.id.task:
                        Intent intentTask = new Intent(AddProjectActivity.this, TaskActivity.class);
                        startActivity(intentTask);
                    default:
                        return true;
                }
            }
        });
    }


    //on chose color dot click
    @Override
    public void onClick(View view) {
        projectColor = ImageViewCompat.getImageTintList((ImageView) view).getDefaultColor();
        color = String.format("#%06X", (0xFFFFFF & projectColor));
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.RIGHT_LEFT,
                new int[]{projectColor, 0xFF131313});

        gd.setCornerRadius(0f);
        findViewById(R.id.constraintLayout7).setBackground(gd);

        imageView34.setColorFilter(projectColor);
        imageView21.setColorFilter(projectColor);
        popupWindow.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_project_menu, menu);
        return true;
    }


    private void saveProject() {

        String title = editTextProjectTitle.getText().toString();

        int timePerDay = (hourPicker.getValue() * 3600000 + minutesPicker.getValue() * 60000);


        if (title.trim().isEmpty()) {
            Toast.makeText(this, "Please insert project title", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ID, id);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_COLOR, color);
        intent.putExtra(EXTRA_TIME, timePerDay);
        setResult(Activity.RESULT_OK, intent);
        finish();

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
