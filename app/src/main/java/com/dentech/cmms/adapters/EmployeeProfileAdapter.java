package com.dentech.cmms.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dentech.cmms.R;
import com.dentech.cmms.model.Employee;
import com.dentech.cmms.viewholders.EmployeeProfileViewHolder;
import com.dentech.cmms.viewholders.EmployeeViewHolder;

import java.util.List;

public class EmployeeProfileAdapter extends RecyclerView.Adapter<EmployeeProfileViewHolder> {
    private Context context;
    private List<Employee> employeeList;

    public EmployeeProfileAdapter(Context context, List<Employee> list){
        this.employeeList = list;
        this.context = context;
    }
    @NonNull
    @Override
    public EmployeeProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_employee_profile, parent, false);
        return new EmployeeProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeProfileViewHolder holder, int position) {
        Employee data  = employeeList.get(position);
        holder.eName.setText(data.getName());
        holder.eContact.setText(data.getEmail());
        holder.eRole.setText(data.getRole());
        holder.eSupervisor.setText(data.getSupervisor());

    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public void clear(){
        employeeList.clear();
        notifyDataSetChanged();

    }
}
