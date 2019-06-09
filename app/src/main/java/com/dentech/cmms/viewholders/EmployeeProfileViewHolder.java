package com.dentech.cmms.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dentech.cmms.R;

public class EmployeeProfileViewHolder extends RecyclerView.ViewHolder {
    public TextView eName, eSupervisor, eContact, eRole;

    public EmployeeProfileViewHolder(@NonNull View itemView) {
        super(itemView);
        eName = (TextView) itemView.findViewById(R.id.tv_user_name);
        eSupervisor = (TextView) itemView.findViewById(R.id.tv_supervisor);
        eContact = (TextView) itemView.findViewById(R.id.tv_contact);
        eRole = (TextView) itemView.findViewById(R.id.tv_role);
    }
}
