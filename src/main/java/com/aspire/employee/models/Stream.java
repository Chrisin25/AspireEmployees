package com.aspire.employee.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="Stream")
public class Stream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer streamId;
    @Column(unique = true)
    String streamName;
    String accountName;

    @ManyToOne
    @JoinColumn(name = "accountName", referencedColumnName = "accountName", insertable = false, updatable = false)
    @JsonIgnore
    private Account account;
    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
