package com.dentech.cmms.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dentech.cmms.R;
import com.dentech.cmms.adapters.EmployeeAdapter;
import com.dentech.cmms.database.DatabaseHelper;
import com.dentech.cmms.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeesActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    Employee employee;
    DatabaseHelper db;
    List<Employee> employeeList;
    EmployeeAdapter employeeAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);
        setTitle("Employees");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        db = new DatabaseHelper(this);
        employee = new Employee();
        employeeList = new ArrayList<>();
        employeeAdapter = new EmployeeAdapter(this, employeeList);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_add_employee);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                //showAddDialog();
                // floatingActionButton.setVisibility(View.INVISIBLE);
                startActivity(new Intent(EmployeesActivity.this, AddEmployees.class));
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.rv_employee);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(employeeAdapter);

        showEmployees();
    }

    @SuppressLint("StaticFieldLeak")
    private void showEmployees() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                employeeList.clear();
                employeeList.addAll(db.getAllEmployees());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                employeeAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
