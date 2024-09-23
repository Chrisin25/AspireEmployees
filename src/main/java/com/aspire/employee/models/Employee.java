package com.aspire.employee.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="Employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer employeeId;
    String employeeName;
    String designation;
    private String streamName;
    Integer managerId;
   

    @ManyToOne
    @JoinColumn(name = "streamName", referencedColumnName = "streamName", insertable = false, updatable = false)
    @JsonIgnore
    private Stream stream;

    private String accountName;

    @ManyToOne
    @JoinColumn(name = "accountName", referencedColumnName = "accountName", insertable = false, updatable = false)
    @JsonIgnore
    private Account account;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }


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

    public String getStreamName() {
        return streamName;
    }

    public void setStream(String stream) {
        this.streamName = stream;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
