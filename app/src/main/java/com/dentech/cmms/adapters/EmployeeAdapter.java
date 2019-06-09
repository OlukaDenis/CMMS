package com.dentech.cmms.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dentech.cmms.R;
import com.dentech.cmms.database.DatabaseHelper;
import com.dentech.cmms.model.Employee;
import com.dentech.cmms.viewholders.EmployeeViewHolder;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeViewHolder> {
    private Context context;
    private List<Employee> employeeList;
    DatabaseHelper db;

    public EmployeeAdapter(Context context, List<Employee> list){
        this.employeeList = list;
        this.context = context;
        db = new DatabaseHelper(context);
    }
    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_employees, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, final int position) {
        final Employee data  = employeeList.get(position);
        holder.eName.setText(data.getName());
        holder.eContact.setText(data.getEmail());
        holder.eRole.setText(data.getRole());
        holder.eSupervisor.setText(data.getSupervisor());
        holder.delete_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteUser(data);
                clear(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public void clear(int position){
        //employeeList.clear();
        notifyItemRemoved(position);
        employeeList.remove(position);
        notifyDataSetChanged();


    }
}
