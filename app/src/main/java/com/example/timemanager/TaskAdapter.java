package com.example.timemanager;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timemanager.entity.Task;
import com.example.timemanager.ui.addproject.AddEditProjectFragment;
import com.example.timemanager.ui.projects.ProjectsFragment;
import com.example.timemanager.viewmodel.TaskViewModel;

public class TaskAdapter extends ListAdapter<Task, TaskAdapter.ViewHolder> {
    private TaskAdapter.OnItemClickListener listener;
    private TaskViewModel taskViewModel;

    public TaskAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Task> DIFF_CALLBACK = new DiffUtil.ItemCallback<Task>() {
        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.isDone() == newItem.isDone();
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_task, parent, false);
        taskViewModel = ProjectsFragment.getTaskViewModel();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task currentTask = getItem(position);
        if (currentTask.isDone()) {
            holder.imageButton.setImageResource(R.drawable.ic_done);
        } else {
            holder.imageButton.setImageResource(R.drawable.ic_circle);
        }
        holder.titleTextView.setText(currentTask.getTitle());
        holder.projectTitleTextView.setText(currentTask.getProjectTitle());
        GradientDrawable drawable = (GradientDrawable) holder.imageView.getBackground();
        drawable.setColor(Color.parseColor(currentTask.getColor()));

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentTask.isDone()) {
                    taskViewModel.isDoneChange(false, currentTask.getId());
                } else {
                    taskViewModel.isDoneChange(true, currentTask.getId());
                }
            }
        });
    }

    public Task getTaskAt(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, projectTitleTextView;
        ImageView imageView;
        ImageButton imageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textView1);
            imageView = itemView.findViewById(R.id.imageView);
            imageButton = itemView.findViewById(R.id.imageButton);
            projectTitleTextView = itemView.findViewById(R.id.textView6);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position), view);
                    }
                }
            });
        }


    }

    public interface OnItemClickListener {
        void onItemClick(Task task, View view);
    }

    public void setOnItemClickListener(TaskAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
