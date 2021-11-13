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
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ProjectAdapter extends ListAdapter<Project, ProjectAdapter.ViewHolder> {


    private OnItemClickListener listener;
    public ProjectAdapter(){
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Project> DIFF_CALLBACK = new DiffUtil.ItemCallback<Project>() {
        @Override
        public boolean areItemsTheSame(@NonNull Project oldItem, @NonNull Project newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Project oldItem, @NonNull Project newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())&&
                    oldItem.getColor().equals(newItem.getColor())&&
                    oldItem.getTime() == newItem.getTime();
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

        holder.timeTextView.setText(String.valueOf(currentproject.getTime()/60)+" : "+(currentproject.getTime()%60));
        holder.progressBar.getProgressDrawable().setColorFilter(Color.parseColor(currentproject.getColor()), PorterDuff.Mode.SRC_IN);

        GradientDrawable drawable = (GradientDrawable)holder.imageView.getBackground();
        drawable.setColor(Color.parseColor(currentproject.getColor()));
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
