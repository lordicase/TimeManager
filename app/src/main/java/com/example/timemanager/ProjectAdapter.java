package com.example.timemanager;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.List;

public class ProjectAdapter extends ListAdapter<Project, ProjectAdapter.ViewHolder> {

    Countdown countdown = null;
    private OnItemClickListener listener;
    int started_Position = -1;

    public ProjectAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Project> DIFF_CALLBACK = new DiffUtil.ItemCallback<Project>() {
        @Override
        public boolean areItemsTheSame(@NonNull Project oldItem, @NonNull Project newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Project oldItem, @NonNull Project newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getColor().equals(newItem.getColor()) &&
                    oldItem.getTime() == newItem.getTime() &&
                    oldItem.getTimeDone() == newItem.getTimeDone();
        }
    };


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_project, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Project currentproject = getItem(position);

        holder.projectTextView.setText(currentproject.getTitle());
        if (currentproject.getTime() <= 3600000) {

            holder.timeTextView.setText(((currentproject.getTimeDone() % 3600000) / 60000 + " : " + ((currentproject.getTimeDone() % 60000) / 1000) + "/" +
                    (currentproject.getTime() % 3600000) / 60000 + " : " + ((currentproject.getTime() % 60000) / 1000)));
            holder.progressBar.getProgressDrawable().setColorFilter(Color.parseColor(currentproject.getColor()), PorterDuff.Mode.SRC_IN);
        } else {
            holder.timeTextView.setText((currentproject.getTimeDone() / 3600000 + " : " + ((currentproject.getTimeDone() % 3600000) / 60000) + "/" +
                    currentproject.getTime() / 3600000 + " : " + ((currentproject.getTime() % 3600000) / 60000)));
            holder.progressBar.getProgressDrawable().setColorFilter(Color.parseColor(currentproject.getColor()), PorterDuff.Mode.SRC_IN);
        }
        holder.progressBar.setMax(currentproject.getTime());
        holder.progressBar.setProgress(currentproject.getTimeDone());


        if (holder.getAdapterPosition() == started_Position) {
            holder.imageButton.setImageResource(R.drawable.ic_round_pause);
        } else {
            holder.imageButton.setImageResource(R.drawable.ic_round_play_arrow);
        }

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countdown != null) {
                    countdown.cancel();
                    notifyItemChanged(started_Position);
                }
                if (holder.getAdapterPosition() == started_Position) {
                    holder.imageButton.setImageResource(R.drawable.ic_round_play_arrow);
                    started_Position = -1;
                } else {
                    started_Position = holder.getAdapterPosition();
                    countdown = new Countdown((currentproject.getTime() - currentproject.getTimeDone()), 1000, currentproject, holder.getAdapterPosition());
                    holder.imageButton.setImageResource(R.drawable.ic_round_pause);
                    countdown.start();
                }
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView projectTextView, timeTextView;
        ImageView imageView;
        ProgressBar progressBar;
        ImageButton imageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            projectTextView = itemView.findViewById(R.id.textView1);
            timeTextView = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);
            progressBar = itemView.findViewById(R.id.progressBar);
            imageButton = itemView.findViewById(R.id.imageButton);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Project project);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
