package com.dentech.cmms.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dentech.cmms.R;

public class EmployeeViewHolder extends RecyclerView.ViewHolder {
    public TextView eName, eSupervisor, eContact, eRole;
    public ImageView delete_employee;

    public EmployeeViewHolder(@NonNull View itemView) {
        super(itemView);
        eName = (TextView) itemView.findViewById(R.id.tv_employee_name);
        eSupervisor = (TextView) itemView.findViewById(R.id.tv_employee_supervisor);
        eContact = (TextView) itemView.findViewById(R.id.tv_employee_contact);
        eRole = (TextView) itemView.findViewById(R.id.tv_employee_role);
        delete_employee = (ImageView) itemView.findViewById(R.id.btn_employee_delete);
    }
}
