package com.tuspring.dto;

import java.sql.Timestamp;

public class Post {
    private long id;
    private long userId;
    private String text;
    private Timestamp timestamp;

    public Post(long userId, String text, Timestamp timestamp) {
        this.userId = userId;
        this.text = text;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "dto.Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", text='" + text + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
