package com.dentech.cmms.admin;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.dentech.cmms.R;
import com.dentech.cmms.database.DatabaseHelper;
import com.dentech.cmms.model.Schedule;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

public class AddMaintenance extends AppCompatActivity {
    private FancyButton btn_add;
    Schedule schedule;
    Calendar calendar;
    int Day, Month, Year;
    DatabaseHelper db;
    List<Schedule> scheduleList;
    MaterialEditText machine, task, employee, type, rating;
    TextView  date, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_maintenance);
        setTitle("Add Schedule");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        db = new DatabaseHelper(this);
        schedule = new Schedule();


        btn_add = (FancyButton) findViewById(R.id.btnAdd_schedule);
        machine= (MaterialEditText) findViewById(R.id.etMachineName);
        date= (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.mTime);
        employee= (MaterialEditText) findViewById(R.id.etEmployee);
        task= (MaterialEditText) findViewById(R.id.etTask);
        type= (MaterialEditText) findViewById(R.id.etMaintenanceType);
        rating = (MaterialEditText) findViewById(R.id.etMachineRating);

        addSchedule();
    }

    private void addSchedule() {

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                Day = calendar.get(Calendar.DAY_OF_MONTH);
                Month = calendar.get(Calendar.MONTH);
                Year = calendar.get(Calendar.YEAR);
                final DatePickerDialog datePickerDialog;
                datePickerDialog = new DatePickerDialog(AddMaintenance.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                date.setText(day + "/" + month + "/" + year);
                            }
                        }, Year, Month, Day);
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddMaintenance.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay +":" +minute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"RestrictedApi", "StaticFieldLeak"})
            @Override
            public void onClick(View v) {
                String machineName = machine.getText().toString();
                String mdate = date.getText().toString();
                String mTime = time.getText().toString();
                String mEmployee = employee.getText().toString();
                String mTask = task.getText().toString();
                String mType = type.getText().toString();
                String mRating = rating.getText().toString();
                if (machineName.isEmpty()) {
                    machine.setError("Please provide machine s!");
                    machine.requestFocus();
                }
                else {
                    schedule.setMachine(machineName);
                    schedule.setDate(mdate);
                    schedule.setTime(mTime);
                    schedule.setEmployee(mEmployee);
                    schedule.setTask(mTask);
                    schedule.setMaintenace_type(mType);
                    schedule.setMachine_rating(mRating);

                    db.addSchedule(schedule);
                    // Toast.makeText(getApplicationContext(), "New project added", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(AddMaintenance.this, MaintenanceSchedule.class));
                    finish();
                }

            }
        });
    }
}
