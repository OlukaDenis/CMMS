package com.dentech.cmms.model;

public class Schedule {
    private int id;
    private String machine, date, time, task, maintenace_type, employee, machine_rating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getMaintenace_type() {
        return maintenace_type;
    }

    public void setMaintenace_type(String maintenace_type) {
        this.maintenace_type = maintenace_type;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getMachine_rating() {
        return machine_rating;
    }

    public void setMachine_rating(String machine_rating) {
        this.machine_rating = machine_rating;
    }
}
