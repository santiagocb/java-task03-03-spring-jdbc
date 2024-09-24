package com.tuspring.dao;

import com.tuspring.dto.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
@Qualifier("userDAO1")
public class UserDAOImpl implements UserDAO {

    private final JdbcTemplate jdbcTemplate;

    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> findUserNamesWithMoreThanFriendsNumberAnd100LikesInTheLasthMonth(long friendShips) {

        var currentDate = LocalDate.now();
        var firstDay = currentDate.withDayOfMonth(1);
        var firstDayOfNextMonth = currentDate.plusMonths(1);

        String sql = """
            SELECT DISTINCT (u.name || ' ' || u.surname) AS fullname
            FROM Users u
            JOIN (
                SELECT f.userid1 AS userid
                FROM Friendships f
                GROUP BY f.userid1
                HAVING COUNT(f.userid1) > ?
            ) friends
            ON u.id = friends.userid
            JOIN (
                SELECT l.userid AS userid
                FROM Likes l
                WHERE l.timestamp >= ? AND l.timestamp < ?
                GROUP BY l.userid
                HAVING COUNT(l.userid) > 100
            ) likes
            ON u.id = likes.userid;
            """;

        return jdbcTemplate.queryForList(sql, String.class, friendShips, Date.valueOf(firstDay), Date.valueOf(firstDayOfNextMonth));
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO Users (name, surname, birthdate) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getSurname(), user.getBirthdate());
    }

    @Override
    public void saveAll(List<User> users) {
        String sql = "INSERT INTO Users (name, surname, birthdate) VALUES (?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, users, users.size(), (ps, user) -> {
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getBirthdate());
        });
    }

    @Override
    public User findById(long id) {
        String sql = "SELECT * FROM Users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM Users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM Users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("birthdate")
            );
        }
    }
}
