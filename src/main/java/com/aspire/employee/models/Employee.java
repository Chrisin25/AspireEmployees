package com.aspire.employee.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="Employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int employeeId;
    String employeeName;
    String designation;
    int managerId;
    @ManyToOne
    @JoinColumn(name = "managerId",insertable=false, updatable=false)
    @JsonIgnore
    private Manager manager;
    String stream;
    @ManyToOne
    @JoinColumn(name = "streamName",insertable=false, updatable=false)
    @JsonIgnore
    private Stream streams;
    String accountName;
    @ManyToOne
    @JoinColumn(name = "accountName",insertable=false, updatable=false)
    @JsonIgnore
    private Account account;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
