package com.dentech.cmms.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dentech.cmms.R;

public class ScheduleViewHolder extends RecyclerView.ViewHolder {
    public TextView machine, date, time, task, type, rating;
    public ImageView deleteTask;

    public ScheduleViewHolder(@NonNull View itemView) {
        super(itemView);
        machine = (TextView) itemView.findViewById(R.id.tv_machine_name);
        date = (TextView) itemView.findViewById(R.id.tv_schedule_date);
        time = (TextView) itemView.findViewById(R.id.tv_schedule_time);
        task = (TextView) itemView.findViewById(R.id.tv_schedule_task);
        type = (TextView) itemView.findViewById(R.id.tv_schedule_type);
        rating = (TextView) itemView.findViewById(R.id.tv_schedule_rating);
        deleteTask = (ImageView) itemView.findViewById(R.id.btn_task_delete);
    }
}
