package com.aspire.employee.models;

import jakarta.persistence.*;

@Entity
@Table(name="Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer accountId;

    @Column(unique = true)
    String accountName;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
