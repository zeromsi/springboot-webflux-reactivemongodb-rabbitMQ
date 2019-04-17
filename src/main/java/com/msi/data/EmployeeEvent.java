package com.msi.data;


import java.util.Date;

public class EmployeeEvent {

    private Notification employee;
    private Date date;

    public EmployeeEvent(Notification employee, Date date) {
        this.employee = employee;
        this.date = date;
    }

    public Notification getEmployee() {
        return employee;
    }

    public void setEmployee(Notification employee) {
        this.employee = employee;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
