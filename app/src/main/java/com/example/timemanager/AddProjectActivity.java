package com.example.timemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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


public class AddProjectActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_TITLE = "com.example.timemanager.EXTRA_TITLE";
    public static final String EXTRA_COLOR = "com.example.timemanager.EXTRA_COLOR";
    public static final String EXTRA_TIME = "com.example.timemanager.EXTRA_TIME";
    int width, height, projectColor = -769226;
    private View popupView;
    private PopupWindow popupWindow;
    private EditText editTextProjectTitle;
    private ImageView imageView34, imageView21;
    private NumberPicker hourPicker, minutesPicker;

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
        popupView = inflater.inflate(R.layout.select_color_popup, null);
        // create the popup window
        width = LinearLayout.LayoutParams.WRAP_CONTENT;
        height = LinearLayout.LayoutParams.WRAP_CONTENT;

        popupView.findViewById(R.id.imageView21).setOnClickListener(this);
        popupView.findViewById(R.id.imageView22).setOnClickListener(this);
        popupView.findViewById(R.id.imageView23).setOnClickListener(this);
        popupView.findViewById(R.id.imageView24).setOnClickListener(this);
        popupView.findViewById(R.id.imageView26).setOnClickListener(this);
        popupView.findViewById(R.id.imageView27).setOnClickListener(this);
        popupView.findViewById(R.id.imageView28).setOnClickListener(this);
        popupView.findViewById(R.id.imageView29).setOnClickListener(this);
        popupView.findViewById(R.id.imageView30).setOnClickListener(this);
        popupView.findViewById(R.id.imageView31).setOnClickListener(this);
        popupView.findViewById(R.id.imageView32).setOnClickListener(this);
        popupView.findViewById(R.id.imageView33).setOnClickListener(this);

        findViewById(R.id.imageView21).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupWindow = new PopupWindow(popupView, width, height, true);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
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
    }


    @Override
    public void onClick(View view) {
        projectColor = ImageViewCompat.getImageTintList((ImageView) view).getDefaultColor();

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
        String color = String.format("#%06X", (0xFFFFFF & projectColor));
        int timePerDay = (hourPicker.getValue() * 3600000 + minutesPicker.getValue()*60000);


        if (title.trim().isEmpty()) {
            Toast.makeText(this, "Please insert project title", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_COLOR, color);
        data.putExtra(EXTRA_TIME, timePerDay);
        setResult(Activity.RESULT_OK, data);
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
