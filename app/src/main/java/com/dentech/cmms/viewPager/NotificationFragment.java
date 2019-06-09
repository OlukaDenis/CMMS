package com.dentech.cmms.viewPager;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dentech.cmms.R;
import com.dentech.cmms.adapters.ScheduleAdapter;
import com.dentech.cmms.common.CommonUtils;
import com.dentech.cmms.database.DatabaseHelper;
import com.dentech.cmms.model.Schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Schedule> scheduleList;
    DatabaseHelper db;
    ScheduleAdapter scheduleAdapter;
    String phone;


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        scheduleList = new ArrayList<>();
        db = new DatabaseHelper(getActivity());
        scheduleAdapter = new ScheduleAdapter(getActivity(), scheduleList);

        recyclerView = (RecyclerView) view.findViewById(R.id.list_notification_recyclerview);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(scheduleAdapter);

        phone = CommonUtils.current_user_contact;

        retrieveSchedule();

        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private void retrieveSchedule() {

        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                scheduleList.clear();
               // scheduleList.addAll(db.getSchedule(phone));
                scheduleList.addAll(db.getAllSchedules());

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
