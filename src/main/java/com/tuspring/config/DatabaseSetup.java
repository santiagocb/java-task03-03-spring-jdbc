package com.tuspring.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSetup {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseSetup(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTables() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS Users (" +
                "id BIGSERIAL PRIMARY KEY, " +
                "name VARCHAR(100), " +
                "surname VARCHAR(100), " +
                "birthdate VARCHAR(100))");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS Friendships (" +
                "userid1 BIGINT, " +
                "userid2 BIGINT, " +
                "timestamp TIMESTAMP, " +
                "PRIMARY KEY (userid1, userid2), " +
                "FOREIGN KEY (userid1) REFERENCES Users(id), " +
                "FOREIGN KEY (userid2) REFERENCES Users(id))");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS Posts (" +
                "id BIGSERIAL PRIMARY KEY, " +
                "userId BIGINT, " +
                "text TEXT, " +
                "timestamp TIMESTAMP, " +
                "FOREIGN KEY (userId) REFERENCES Users(id))");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS Likes (" +
                "postid BIGINT, " +
                "userid BIGINT, " +
                "timestamp TIMESTAMP, " +
                "FOREIGN KEY (postid) REFERENCES Posts(id), " +
                "FOREIGN KEY (userid) REFERENCES Users(id))");
    }

    public void dropTables() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS Likes");
        jdbcTemplate.execute("DROP TABLE IF EXISTS Posts");
        jdbcTemplate.execute("DROP TABLE IF EXISTS Friendships");
        jdbcTemplate.execute("DROP TABLE IF EXISTS Users");
    }
}
