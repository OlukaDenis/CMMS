package com.dentech.cmms.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dentech.cmms.R;
import com.dentech.cmms.model.Schedule;
import com.dentech.cmms.viewholders.NotificationViewHolder;
import com.dentech.cmms.viewholders.ScheduleViewHolder;

import java.util.List;

public class UserNotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {
    private Context context;
    private List<Schedule> scheduleList;

    public UserNotificationAdapter(Context context, List<Schedule> list){
        this.scheduleList = list;
        this.context = context;
    }
    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Schedule data  = scheduleList.get(position);
        holder.machine.setText(data.getMachine());
        holder.task.setText(data.getTask());
        holder.date.setText(data.getDate());
        holder.time.setText(data.getTime());
        holder.type.setText(data.getMaintenace_type());
        holder.rating.setText(data.getMachine_rating());

    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public void clear(){
        scheduleList.clear();
        notifyDataSetChanged();

    }
}
