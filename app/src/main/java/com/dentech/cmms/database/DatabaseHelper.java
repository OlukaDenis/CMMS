package com.dentech.cmms.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dentech.cmms.model.Admin;
import com.dentech.cmms.model.Employee;
import com.dentech.cmms.model.Schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "test4.db";

    // Tables
    private static final String TABLE_USER = "user";
    private static final String TABLE_ADMIN = "admin";
    private static final String TABLE_SCHEDULE = "schedule";

    // Admin Table Columns names
    private static final String COLUMN_ADMIN_ID = "admin_id";
    private static final String COLUMN_ADMIN_NAME = "admin_name";
    private static final String COLUMN_ADMIN_PHONE = "admin_email";
    private static final String COLUMN_ADMIN_PASSWORD = "admin_password";

    // Employee Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_PHONE = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_SUPERVISOR = "user_supervisor";
    private static final String COLUMN_USER_ROLE = "user_role";

    //Schedule table column names
    private static final String COLUMN_SCHEDULE_ID = "schedule_id";
    private static final String COLUMN_SCHEDULE_MACHINE = "schedule_machine";
    private static final String COLUMN_SCHEDULE_DATE = "schedule_date";
    private static final String COLUMN_SCHEDULE_MAINTENANCE_TYPE = "schedule_maintenance_type";
    private static final String COLUMN_SCHEDULE_TIME = "schedule_time";
    private static final String COLUMN_SCHEDULE_MACHINE_RATING = "schedule_machine_rating";
    private static final String COLUMN_SCHEDULE_EMPLOYEE = "schedule_employee";
    private static final String COLUMN_SCHEDULE_TASK = "schedule_task";

    // create admin table sql query
    private String CREATE_ADMIN_TABLE = "CREATE TABLE " + TABLE_ADMIN + "("
            + COLUMN_ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ADMIN_NAME + " TEXT,"
            + COLUMN_ADMIN_PHONE + " TEXT," + COLUMN_ADMIN_PASSWORD + " TEXT" + ")";

    // create user table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_PHONE + " TEXT," + COLUMN_USER_ROLE + " TEXT," + COLUMN_USER_SUPERVISOR + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT" + ")";

    //Creating project table
    private String CREATE_SCHEDULE_TABLE = "CREATE TABLE " + TABLE_SCHEDULE+ "("
            + COLUMN_SCHEDULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_SCHEDULE_MACHINE + " TEXT,"
            + COLUMN_SCHEDULE_DATE + " TEXT," + COLUMN_SCHEDULE_TIME + " TEXT," + COLUMN_SCHEDULE_MAINTENANCE_TYPE + " TEXT,"
            + COLUMN_SCHEDULE_EMPLOYEE + " TEXT," + COLUMN_SCHEDULE_MACHINE_RATING + " TEXT,"
            + COLUMN_SCHEDULE_TASK + " TEXT," + " TEXT" + ")";

    // drop table sql query
    private String DROP_ADMIN_TABLE = "DROP TABLE IF EXISTS " + TABLE_ADMIN;
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_SCHEDULE_TABLE = "DROP TABLE IF EXISTS " + TABLE_SCHEDULE;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ADMIN_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_SCHEDULE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop Employee Table if exist
        db.execSQL(DROP_ADMIN_TABLE);
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_SCHEDULE_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param admin
     */
    public void addAdmin(Admin admin) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ADMIN_NAME, admin.getName());
        values.put(COLUMN_ADMIN_PHONE, admin.getPhone());
        values.put(COLUMN_ADMIN_PASSWORD, admin.getPassword());

        // Inserting Row
        db.insert(TABLE_ADMIN, null, values);
        db.close();
    }

    /**
     * This method is to create employee record
     *
     * @param employee
     */
    public void addEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, employee.getName());
        values.put(COLUMN_USER_PHONE, employee.getEmail());
        values.put(COLUMN_USER_ROLE, employee.getRole());
        values.put(COLUMN_USER_SUPERVISOR, employee.getSupervisor());
        values.put(COLUMN_USER_PASSWORD, employee.getPassword());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<Employee> getAllEmployees() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_USER_PHONE,
                COLUMN_USER_ROLE,
                COLUMN_USER_SUPERVISOR,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder = COLUMN_USER_NAME + " ASC";
        List<Employee> employeeList = new ArrayList<Employee>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                employee.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                employee.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                employee.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PHONE)));
                employee.setRole(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ROLE)));
                employee.setSupervisor(cursor.getString(cursor.getColumnIndex(COLUMN_USER_SUPERVISOR)));
                employee.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding employee record to list
                employeeList.add(employee);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return employeeList;
    }

    /**
     * This method retrieves all schedules belonging to an employee
     */
    public List<Employee> getEmployee(String phone) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_USER_PHONE,
                COLUMN_USER_ROLE,
                COLUMN_USER_SUPERVISOR,
                COLUMN_USER_PASSWORD

        };
        // sorting orders
        String sortOrder = COLUMN_USER_NAME + " ASC";

        // selection criteria
        String selection = COLUMN_USER_PHONE + " = ?";

        // selection arguments
        String[] selectionArgs = {phone};

        List<Employee> employeeList = new ArrayList<Employee>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the project table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                employee.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                employee.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                employee.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PHONE)));
                employee.setRole(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ROLE)));
                employee.setSupervisor(cursor.getString(cursor.getColumnIndex(COLUMN_USER_SUPERVISOR)));
                employee.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding employee record to list
                employeeList.add(employee);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return schedule  list
        return employeeList;
    }

    /**
     * This method to update employee record
     *
     * @param employee
     */
    public void updateUser(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, employee.getName());
        values.put(COLUMN_USER_PHONE, employee.getEmail());
        values.put(COLUMN_USER_PASSWORD, employee.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(employee.getId())});
        db.close();
    }

    /**
     * This method is to delete employee record
     *
     * @param employee
     */
    public void deleteUser(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete employee record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(employee.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_PHONE + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    /**
     * This method to check user exist or not
     *
     * @param phone
     * @return true/false
     */
    public boolean checkAdmin(String phone) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_ADMIN_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_ADMIN_PHONE + " = ?";

        // selection argument
        String[] selectionArgs = {phone};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_ADMIN, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }



    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkEmployee(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_PHONE+ " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check admin exist or not
     *
     * @param phone
     * @param password
     * @return true/false
     */
    public boolean checkAdmin(String phone, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_ADMIN_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_ADMIN_PHONE+ " = ?" + " AND " + COLUMN_ADMIN_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {phone, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_ADMIN, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    /**
     * This method to add a new schedule
     */
    public void addSchedule(Schedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SCHEDULE_MACHINE, schedule.getMachine());
        values.put(COLUMN_SCHEDULE_DATE, schedule.getDate());
        values.put(COLUMN_SCHEDULE_TIME, schedule.getTime());
        values.put(COLUMN_SCHEDULE_MACHINE_RATING, schedule.getMachine_rating());
        values.put(COLUMN_SCHEDULE_MAINTENANCE_TYPE, schedule.getMaintenace_type());
        values.put(COLUMN_SCHEDULE_EMPLOYEE, schedule.getEmployee());
        values.put(COLUMN_SCHEDULE_TASK, schedule.getTask());

        // Inserting Row
        db.insert(TABLE_SCHEDULE, null, values);
        db.close();
    }

    /**
     * This method retrieves all schedules belonging to an employee
     */
    public List<Schedule> getAllSchedules(){
        // array of columns to fetch
        String[] columns = {
                COLUMN_SCHEDULE_ID,
                COLUMN_SCHEDULE_MACHINE,
                COLUMN_SCHEDULE_DATE,
                COLUMN_SCHEDULE_TIME,
                COLUMN_SCHEDULE_EMPLOYEE,
                COLUMN_SCHEDULE_TASK,
                COLUMN_SCHEDULE_MAINTENANCE_TYPE,
                COLUMN_SCHEDULE_MACHINE_RATING

        };
        // sorting orders
        String sortOrder = COLUMN_SCHEDULE_MACHINE + " ASC";
        List<Schedule> scheduleList = new ArrayList<Schedule>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the project table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_SCHEDULE, //Table to query
                columns,    //columns to return
                null,                  //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Schedule schedule = new Schedule();
                schedule.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_ID))));
                schedule.setMachine(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_MACHINE)));
                schedule.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_DATE)));
                schedule.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_TIME)));
                schedule.setEmployee(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_EMPLOYEE)));
                schedule.setTask(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_TASK)));
                schedule.setMaintenace_type(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_MAINTENANCE_TYPE)));
                schedule.setMachine_rating(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_MACHINE_RATING)));
                // Adding schedule record to list
                scheduleList.add(schedule);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return schedule  list
        return scheduleList;
    }

    /**
     * This method retrieves all schedules belonging to an employee
     */
    public List<Schedule> getSchedule(String phone) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_SCHEDULE_ID,
                COLUMN_SCHEDULE_MACHINE,
                COLUMN_SCHEDULE_DATE,
                COLUMN_SCHEDULE_TIME,
                COLUMN_SCHEDULE_EMPLOYEE,
                COLUMN_SCHEDULE_TASK,
                COLUMN_SCHEDULE_MAINTENANCE_TYPE,
                COLUMN_SCHEDULE_MACHINE_RATING

        };
        // sorting orders
        String sortOrder = COLUMN_SCHEDULE_DATE + " ASC";

        // selection criteria
        String selection = COLUMN_SCHEDULE_EMPLOYEE+ " = ?";

        // selection arguments
        String[] selectionArgs = {phone};

        List<Schedule> scheduleList = new ArrayList<Schedule>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the project table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_SCHEDULE, //Table to query
                columns,    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Schedule schedule = new Schedule();
                schedule.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_ID))));
                schedule.setMachine(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_MACHINE)));
                schedule.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_DATE)));
                schedule.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_TIME)));
                schedule.setEmployee(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_EMPLOYEE)));
                schedule.setTask(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_TASK)));
                schedule.setMaintenace_type(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_MAINTENANCE_TYPE)));
                schedule.setMachine_rating(cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE_MACHINE_RATING)));
                // Adding schedule record to list
                scheduleList.add(schedule);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return schedule  list
        return scheduleList;
    }

    /**
     * This method is to delete schedule record
     *
     * @param schedule
     */
    public void deleteSchedule(Schedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete schedule record by id
        db.delete(TABLE_SCHEDULE, COLUMN_SCHEDULE_ID + " = ?",
                new String[]{String.valueOf(schedule.getId())});
        db.close();
    }


    /**
     * get datetime
     * */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}