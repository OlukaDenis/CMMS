package com.dentech.cmms.navigationdrawer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dentech.cmms.EmployeeNotifications;
import com.dentech.cmms.R;
import com.dentech.cmms.StartActivity;
import com.dentech.cmms.adapters.EmployeeAdapter;
import com.dentech.cmms.adapters.EmployeeProfileAdapter;
import com.dentech.cmms.admin.MainActivity;
import com.dentech.cmms.common.CommonUtils;
import com.dentech.cmms.database.DatabaseHelper;
import com.dentech.cmms.model.Employee;
import com.dentech.cmms.viewPager.SimpleFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class EmployeeHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Employee employee;
    DatabaseHelper db;
    List<Employee> employeeList;
    EmployeeProfileAdapter employeeAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String phone;
    private boolean allowBackButtonExit = false;
    TextView currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("Employee Portal");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //showing the users full name on the header
        View headerView = navigationView.getHeaderView(0);
        currentUser = headerView.findViewById(R.id.current_employee);
        currentUser.setText(CommonUtils.current_user_contact);


        db = new DatabaseHelper(this);
        employee = new Employee();
        employeeList = new ArrayList<>();
        employeeAdapter = new EmployeeProfileAdapter(this, employeeList);
        phone = CommonUtils.current_user_contact;

        recyclerView = (RecyclerView) findViewById(R.id.rv_profile);
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
                employeeList.addAll(db.getEmployee(phone));

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                employeeAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (!allowBackButtonExit) {
                allowBackButtonExit = true;
                Toast.makeText(this, "Press again to close", Toast.LENGTH_SHORT).show();
                CountDownTimer timer = new CountDownTimer(1000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        allowBackButtonExit = false;
                    }
                }.start();
            } else {
                System.exit(0);
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_notifications) {
            startActivity(new Intent(EmployeeHomeActivity.this, EmployeeNotifications.class));
        } else if(id == R.id.nav_logout){
            Intent intent = new Intent(EmployeeHomeActivity.this, StartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Toast.makeText(EmployeeHomeActivity.this, "You've been logged out!", Toast.LENGTH_SHORT)
                    .show();
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
