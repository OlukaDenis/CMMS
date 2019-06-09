package com.dentech.cmms.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dentech.cmms.R;
import com.dentech.cmms.database.DatabaseHelper;
import com.dentech.cmms.model.Employee;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

public class AddEmployees extends AppCompatActivity {
    private FancyButton btn_add;
    Employee employee;
    DatabaseHelper db;
    List<Employee> projectList;
    MaterialEditText employee_name, employee_role, employee_contact, employee_supervisor, employee_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employees);
        setTitle("Add Employees");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       //this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        db = new DatabaseHelper(this);
        employee = new Employee();


        btn_add = (FancyButton) findViewById(R.id.btnAdd_employee);
        employee_name= (MaterialEditText) findViewById(R.id.etEmployeeName);
        employee_contact= (MaterialEditText) findViewById(R.id.etEmployeeContact);
        employee_role= (MaterialEditText) findViewById(R.id.etEmployeeRole);
        employee_supervisor= (MaterialEditText) findViewById(R.id.etEmployeeSupervisor);
        employee_password= (MaterialEditText) findViewById(R.id.etEmployeePassword);

        addEmployee();
    }

    public void addEmployee(){

        btn_add.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"RestrictedApi", "StaticFieldLeak"})
            @Override
            public void onClick(View v) {
                String mEname = employee_name.getText().toString();
                String mRole = employee_role.getText().toString();
                String mContact = employee_contact.getText().toString();
                String mSupervisor = employee_supervisor.getText().toString();
                String mPass = employee_password.getText().toString();
                if (mEname.isEmpty()) {
                    employee_name.setError("Please provide project title!");
                    employee_name.requestFocus();
                }
                else {
                    employee.setName(mEname);
                    employee.setEmail(mContact);
                    employee.setRole(mRole);
                    employee.setSupervisor(mSupervisor);
                    employee.setPassword(mPass);

                    db.addEmployee(employee);
                    // Toast.makeText(getApplicationContext(), "New project added", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(AddEmployees.this, EmployeesActivity.class));
                    finish();
                }

            }
        });
    }
}
