package com.tuspring.config;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DataGeneratorWithProcedures {

    private static final int NUM_USERS = 1000;
    private static final int NUM_FRIENDSHIPS = 70000;
    private static final int NUM_POSTS = 1000;
    private static final int NUM_LIKES = 300000;

    private final Connection connection;

    public DataGeneratorWithProcedures(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }

    public void generateUsers() throws SQLException {
        Random random = new Random();
        String[] names = {"Jhon", "Janeth", "Miguel", "Sara", "Roberto", "Emilia", "David", "Laura", "Daniel", "Andres", "Jesus", "Julian"};
        String[] surnames = {"Ramirez", "Castro", "Ortega", "Juarez", "Rendon", "Bustamante", "Garcia", "Rodriguez", "Hurtado", "Florez", "Repetto"};

        String sql = "CALL insertUser(?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < NUM_USERS; i++) {
                String name = names[random.nextInt(names.length)];
                String surname = surnames[random.nextInt(surnames.length)];
                String birthdate = getRandomBirthdate(random);

                ps.setString(1, name);
                ps.setString(2, surname);
                ps.setString(3, birthdate);
                ps.addBatch();

                if (i % 1000 == 0) {
                    ps.executeBatch();
                }
            }
            ps.executeBatch();
        }
    }

    private String getRandomBirthdate(Random random) {
        int year = 1970 + random.nextInt(31);
        int month = 1 + random.nextInt(12);
        int day = 1 + random.nextInt(28);
        return year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
    }

    public void generateFriendships() throws SQLException {
        Random random = new Random();
        String sql = "CALL insertFriendship(?, ?, ?)";

        Set<String> friendshipSet = new HashSet<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int friendshipCount = 0;
            while (friendshipCount < NUM_FRIENDSHIPS) {
                int userId1 = random.nextInt(NUM_USERS) + 1;
                int userId2;

                do {
                    userId2 = random.nextInt(NUM_USERS) + 1;
                } while (userId1 == userId2 || friendshipSet.contains(userId1 + "-" + userId2) || friendshipSet.contains(userId2 + "-" + userId1));

                friendshipSet.add(userId1 + "-" + userId2);

                ps.setInt(1, userId1);
                ps.setInt(2, userId2);
                ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                ps.addBatch();

                friendshipCount++;

                if (friendshipCount % 1000 == 0) {
                    ps.executeBatch();
                }
            }
            ps.executeBatch();
        }
    }

    public void generatePosts() throws SQLException {
        Random random = new Random();
        String sql = "CALL insertPost(?, ?, ?)";


        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < NUM_POSTS; i++) {
                int userId = random.nextInt(NUM_USERS) + 1;
                String postText = "This is post #" + (i + 1) + " by user " + userId;

                ps.setInt(1, userId);
                ps.setString(2, postText);
                ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                ps.addBatch();

                if (i % 1000 == 0) {
                    ps.executeBatch();
                }
            }
            ps.executeBatch();
        }
    }

    public void generateLikes() throws SQLException {
        Random random = new Random();
        String sql = "CALL insertLike(?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < NUM_LIKES; i++) {
                int postId = random.nextInt(NUM_POSTS) + 1;
                int userId = random.nextInt(NUM_USERS) + 1;

                ps.setInt(1, postId);
                ps.setInt(2, userId);
                ps.setTimestamp(3, getRandomTimestamp());
                ps.addBatch();

                if (i % 1000 == 0) {
                    ps.executeBatch();
                }
            }
            ps.executeBatch();
        }
    }

    private static Timestamp getRandomTimestamp() {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime twoMonthsAgo = now.minusMonths(2);

        long nowMillis = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long fiveMonthsAgoMillis = twoMonthsAgo.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        long randomMillis = ThreadLocalRandom.current().nextLong(fiveMonthsAgoMillis, nowMillis);

        return new Timestamp(randomMillis);
    }
}
