package com.example.timemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Project_Adapter extends RecyclerView.Adapter<Project_Adapter.ViewHolder> {

    String project[], time[];
    Context context;

    public Project_Adapter(Context ct, String pro[], String tm[]){
    context = ct;
    project=pro;
    time=tm;

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
    }

    @Override
    public int getItemCount() {
        return project.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView project_tv, time_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            project_tv = itemView.findViewById(R.id.textView1);
            time_tv = itemView.findViewById(R.id.textView2);
        }
    }
}
