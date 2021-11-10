package com.example.timemanager;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ImageViewCompat;


public class AddProjectActivity extends AppCompatActivity implements View.OnClickListener {
    ConstraintLayout layout;
    int width, height;
    View popupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        EditText project_title = findViewById(R.id.editText);

        project_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!String.valueOf(project_title.getText()).equals("")) {
                    findViewById(R.id.save_button).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.save_button).setVisibility(View.INVISIBLE);
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

                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

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
        int color = ImageViewCompat.getImageTintList((ImageView) view).getDefaultColor();

        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.RIGHT_LEFT,
                new int[]{color, 0xFF131313});

        gd.setCornerRadius(0f);
        findViewById(R.id.constraintLayout7).setBackground(gd);

        ImageView imageView34 = findViewById(R.id.imageView34);
        ImageView imageView21 = findViewById(R.id.imageView21);
        imageView34.setColorFilter(color);
        imageView21.setColorFilter(color);
    }


}
