package com.example.timemanager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Project_RecyclerViev extends RecyclerView.Adapter<Project_RecyclerViev.ViewHolder> {

    String project[], time[];
    Context context;

    public Project_RecyclerViev(Context ct, String pro[], String tm[]){
    context = ct;
    project=pro;
    time=tm;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
