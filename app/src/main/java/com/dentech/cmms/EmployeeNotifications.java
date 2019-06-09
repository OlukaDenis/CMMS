package com.dentech.cmms;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dentech.cmms.adapters.ScheduleAdapter;
import com.dentech.cmms.adapters.UserNotificationAdapter;
import com.dentech.cmms.common.CommonUtils;
import com.dentech.cmms.database.DatabaseHelper;
import com.dentech.cmms.model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class EmployeeNotifications extends AppCompatActivity {
    Schedule schedule;
    DatabaseHelper db;
    List<Schedule> scheduleList;
    UserNotificationAdapter notificationAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_notifications);

        setTitle("Notifications");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        db = new DatabaseHelper(this);
        schedule = new Schedule();
        scheduleList = new ArrayList<>();
        notificationAdapter = new UserNotificationAdapter(this, scheduleList);
        phone = CommonUtils.current_user_contact;

        recyclerView = (RecyclerView) findViewById(R.id.rv_schedule_notifications);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(notificationAdapter);

        showSchedules();
    }

    @SuppressLint("StaticFieldLeak")
    private void showSchedules() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                scheduleList.clear();
                //scheduleList.addAll(db.getAllSchedules());
               scheduleList.addAll(db.getSchedule(phone));

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                notificationAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
