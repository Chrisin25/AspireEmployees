package com.aspire.employee.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Manager {
    @Id
    int managerId;
    String name;
    String accountName;
    String stream;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }
}
