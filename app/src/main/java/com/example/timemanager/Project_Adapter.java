package com.example.timemanager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Project_Adapter extends RecyclerView.Adapter<Project_Adapter.ViewHolder> {

    String project[], time[], color[];
    Context context;

    public Project_Adapter(Context ct, String pro[], String tm[], String col[]){
    context = ct;
    project=pro;
    time=tm;
    color=col;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_project, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.project_tv.setText(project[position]);
        holder.time_tv.setText(time[position]);

        GradientDrawable drawable = (GradientDrawable)holder.imageView.getBackground();
        drawable.setStroke(2, Color.parseColor(color[position])); // set stroke width and stroke color
    }

    @Override
    public int getItemCount() {
        return project.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView project_tv, time_tv;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            project_tv = itemView.findViewById(R.id.textView1);
            time_tv = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
