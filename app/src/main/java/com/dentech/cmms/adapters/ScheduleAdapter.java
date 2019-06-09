package com.dentech.cmms.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dentech.cmms.R;
import com.dentech.cmms.database.DatabaseHelper;
import com.dentech.cmms.model.Schedule;
import com.dentech.cmms.viewholders.ScheduleViewHolder;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleViewHolder> {
    private Context context;
    private List<Schedule> scheduleList;
    DatabaseHelper db;

    public ScheduleAdapter(Context context, List<Schedule> list){
        this.scheduleList = list;
        this.context = context;
        db = new DatabaseHelper(context);
    }
    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layou_list_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, final int position) {
        final Schedule data  = scheduleList.get(position);
        holder.machine.setText(data.getMachine());
        holder.task.setText(data.getTask());
        holder.date.setText(data.getDate());
        holder.time.setText(data.getTime());
        holder.type.setText(data.getMaintenace_type());
        holder.rating.setText(data.getMachine_rating());
        holder.deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteSchedule(data);
                clear(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public void clear(int position){
        //employeeList.clear();
        notifyItemRemoved(position);
        scheduleList.remove(position);
        notifyDataSetChanged();
    }
}
