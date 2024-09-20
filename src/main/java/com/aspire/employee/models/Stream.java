package com.aspire.employee.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Stream {
    @Id
    int streamId;
    String streamName;

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }
}
