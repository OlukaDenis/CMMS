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
import com.dentech.cmms.adapters.ScheduleAdapter;
import com.dentech.cmms.database.DatabaseHelper;
import com.dentech.cmms.model.Employee;
import com.dentech.cmms.model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class MaintenanceSchedule extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    Schedule schedule;
    DatabaseHelper db;
    List<Schedule> scheduleList;
    ScheduleAdapter scheduleAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_schedule);

        setTitle("Maintenance Schedule");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        db = new DatabaseHelper(this);
        schedule = new Schedule();
        scheduleList = new ArrayList<>();
        scheduleAdapter = new ScheduleAdapter(this, scheduleList);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_add_schedule);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                //showAddDialog();
                // floatingActionButton.setVisibility(View.INVISIBLE);
                startActivity(new Intent(MaintenanceSchedule.this, AddMaintenance.class));
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.rv_schedule);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(scheduleAdapter);

        showSchedules();
    }

    @SuppressLint("StaticFieldLeak")
    private void showSchedules() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                scheduleList.clear();
               scheduleList.addAll(db.getAllSchedules());
               // scheduleList.addAll(db.getSchedule("0779275749"));

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                scheduleAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
