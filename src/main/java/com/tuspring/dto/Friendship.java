package com.tuspring.dto;

import java.sql.Timestamp;

public class Friendship {
    private long userid1;
    private long userid2;
    private Timestamp timestamp;

    public Friendship(long userid1, long userid2, Timestamp timestamp) {
        this.userid1 = userid1;
        this.userid2 = userid2;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public long getUserid1() {
        return userid1;
    }

    public void setUserid1(long userid1) {
        this.userid1 = userid1;
    }

    public long getUserid2() {
        return userid2;
    }

    public void setUserid2(long userid2) {
        this.userid2 = userid2;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "dto.Friendship{" +
                "userid1=" + userid1 +
                ", userid2=" + userid2 +
                ", timestamp=" + timestamp +
                '}';
    }
}
