package com.example.timemanager;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.timemanager.entity.Project;
import com.example.timemanager.entity.ProjectSession;
import com.example.timemanager.ui.projects.ProjectsFragment;
import com.example.timemanager.viewmodel.ProjectSessionViewModel;

import java.util.Date;

public class ProjectAdapter extends ListAdapter<Project, ProjectAdapter.ViewHolder> {


    private OnItemClickListener listener;
    int startedPosition = MainActivity.getStartedPosition();
    int startedWorker = MainActivity.getStartedWorker();
    ProjectSessionViewModel projectSessionViewModel;


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
        projectSessionViewModel = ProjectsFragment.getProjectSessionViewModel();
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Project currentproject = getItem(position);

        holder.projectTextView.setText(currentproject.getTitle());


        if (currentproject.getTimeDone() >= currentproject.getTime()) {
            holder.imageButton.setImageResource(R.drawable.ic_round_play_arrow);
            holder.imageButton.setEnabled(false);
            holder.timeTextView.setText("COMPLETED!");


        } else {
            holder.timeTextView.setText(getTime(currentproject.getTimeDone()) + " : " + getTime(currentproject.getTime()));

            holder.imageButton.setEnabled(true);
            if (holder.getAdapterPosition() == startedPosition) {
                holder.imageButton.setImageResource(R.drawable.ic_round_pause);
            } else {
                holder.imageButton.setImageResource(R.drawable.ic_round_play_arrow);
            }
        }

        holder.progressBar.getProgressDrawable().setColorFilter(Color.parseColor(currentproject.getColor()), PorterDuff.Mode.SRC_IN);
        holder.progressBar.setMax(currentproject.getTime());
        holder.progressBar.setProgress(currentproject.getTimeDone());


        GradientDrawable drawable = (GradientDrawable) holder.imageView.getBackground();
        drawable.setColor(Color.parseColor(currentproject.getColor()));

        holder.imageButton.setOnClickListener(view -> {
            WorkManager.getInstance(view.getContext()).cancelUniqueWork(String.valueOf(startedWorker));

            if (holder.getAdapterPosition() == startedPosition) {
                startedPosition = -1;
                startedWorker++;
                holder.imageButton.setImageResource(R.drawable.ic_round_play_arrow);
                notifyItemChanged(holder.getAdapterPosition());


            } else {
                long startTime = new Date().getTime();
                projectSessionViewModel.insert(new ProjectSession(currentproject.getId(), currentproject.getTitle(), startTime));
                notifyItemChanged(startedPosition);
                startedPosition = holder.getAdapterPosition();

                Toast.makeText(view.getContext(), "Starting " + currentproject.getTitle(), Toast.LENGTH_SHORT).show();
                holder.imageButton.setImageResource(R.drawable.ic_round_pause);
                startedWorker++;
                Data projectData = new Data.Builder()
                        .putInt("id", currentproject.getId())
                        .putInt("time", currentproject.getTime())
                        .putInt("timeDone", currentproject.getTimeDone())
                        .putString("title", currentproject.getTitle())
                        .putString("color", currentproject.getColor())
                        .putString("days", currentproject.getDays())
                        .putLong("sessionStartTime", startTime)
                        .putInt("startedPosition", startedPosition)
                        .putInt("startedWorker", startedWorker)
                        .build();


                OneTimeWorkRequest uploadWorkRequest =
                        new OneTimeWorkRequest.Builder(CountdownWorker.class)
                                .setConstraints(new Constraints.Builder()

                                        .build())
                                .setInputData(projectData)
                                .addTag(String.valueOf(startedWorker))
                                .build();

                WorkManager
                        .getInstance(view.getContext())
                        .enqueueUniqueWork(String.valueOf(startedWorker), ExistingWorkPolicy.REPLACE, uploadWorkRequest);

            }
            MainActivity.setStartedPosition(startedPosition);
            MainActivity.setStartedWorker(startedWorker);
        });

    }

    private String getTime(int time) {
        time /= 1000;
        String timeS;
        if (time >= 86400) {
            timeS = time / 86400 + "d " + time % 86400 / 3600 + "h";
        } else if (time >= 3600) {
            timeS = time / 3600 + "h " + time % 3600 / 60 + "min";
        } else if (time >= 60) {
            timeS = time / 60 + "min " + time % 60 + "s";
        } else {
            timeS = time % 60 + "s";
        }
        return timeS;
    }

    public Project getProjectAt(int position) {
        return getItem(position);
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


            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
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
