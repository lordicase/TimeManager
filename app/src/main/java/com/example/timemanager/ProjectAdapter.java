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

public class ProjectAdapter extends ListAdapter<Project, ProjectAdapter.ViewHolder> {


    private OnItemClickListener listener;
    int startedPosition = MainActivity.getStartedPosition();
     int startedWorker = MainActivity.getStartedWorker();



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


        if (currentproject.getTimeDone() >= currentproject.getTime()) {
            holder.imageButton.setImageResource(R.drawable.ic_round_play_arrow);
            holder.imageButton.setEnabled(false);
            holder.timeTextView.setText("COMPLETED!");


        } else {

                holder.timeTextView.setText((currentproject.getTimeDone() / 3600000) + " : " + ((currentproject.getTimeDone() % 3600000) / 60000) + " : " + ((currentproject.getTimeDone() % 60000) / 1000) + " / " +
                        (currentproject.getTime() / 3600000) + " : " + ((currentproject.getTime() % 3600000) / 60000) + " : " + ((currentproject.getTime() % 60000) / 1000));

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

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance(view.getContext()).cancelUniqueWork(String.valueOf(startedWorker));

                if (holder.getAdapterPosition() == startedPosition) {
                    startedPosition = -1;
                    startedWorker++;
                    holder.imageButton.setImageResource(R.drawable.ic_round_play_arrow);
                    notifyItemChanged(holder.getAdapterPosition());
                } else {
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

            }
        });

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
