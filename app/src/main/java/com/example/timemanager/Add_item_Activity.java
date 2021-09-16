package com.example.timemanager;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ImageViewCompat;

import static androidx.core.view.ViewCompat.getBackgroundTintList;


public class Add_item_Activity extends AppCompatActivity implements View.OnClickListener {
    ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        layout=findViewById(R.id.constraintLayout7);
        findViewById(R.id.imageView21).setOnClickListener(this);
        findViewById(R.id.imageView22).setOnClickListener(this);
        findViewById(R.id.imageView23).setOnClickListener(this);
        findViewById(R.id.imageView24).setOnClickListener(this);
        findViewById(R.id.imageView26).setOnClickListener(this);
        findViewById(R.id.imageView27).setOnClickListener(this);
        findViewById(R.id.imageView28).setOnClickListener(this);
        findViewById(R.id.imageView29).setOnClickListener(this);
        findViewById(R.id.imageView30).setOnClickListener(this);
        findViewById(R.id.imageView31).setOnClickListener(this);
        findViewById(R.id.imageView32).setOnClickListener(this);
        findViewById(R.id.imageView33).setOnClickListener(this);
    }

    void change_color(int color){
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.RIGHT_LEFT,
                new int[] {color,0xFF131313});
        gd.setCornerRadius(0f);
        layout.setBackground(gd);

        ImageView x = findViewById(R.id.imageView34);
        x.setColorFilter(color);
    }

    @Override
    public void onClick(View view) {
        int color = ImageViewCompat.getImageTintList((ImageView) view).getDefaultColor();

        change_color(color);
    }
}
