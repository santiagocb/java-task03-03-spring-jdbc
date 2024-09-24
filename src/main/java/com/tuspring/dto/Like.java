package com.tuspring.dto;

import java.sql.Timestamp;

public class Like {
    private long postId;
    private long userId;
    private Timestamp timestamp;

    public Like(long postId, long userId, Timestamp timestamp) {
        this.postId = postId;
        this.userId = userId;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "dto.Like{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", timestamp=" + timestamp +
                '}';
    }
}
