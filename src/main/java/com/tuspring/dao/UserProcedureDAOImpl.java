package com.tuspring.dao;

import com.tuspring.dto.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserProcedureDAOImpl implements UserDAO {

    private final JdbcTemplate jdbcTemplate;

    private final Connection con;

    public UserProcedureDAOImpl(JdbcTemplate jdbcTemplate) throws SQLException {
        this.jdbcTemplate = jdbcTemplate;
        this.con = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
    }

    public List<String> findUserNamesWithMoreThanFriendsNumberAnd100LikesInTheLasthMonth(long friendShips) {

        var timestampFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        var currentDate = LocalDate.now();
        var firstDay = currentDate.atStartOfDay().withDayOfMonth(1).format(timestampFormatter);
        var firstDayOfNextMonth = currentDate.atStartOfDay().plusMonths(1).format(timestampFormatter);

        String GET_USER_NAMES = "SELECT * FROM selectFullnamesWithHigherFriendsAndLikes(?, ?, ?)";

        List<String> names = new ArrayList<>();

        try(PreparedStatement statement = con.prepareStatement(GET_USER_NAMES);) {
            statement.setLong(1, friendShips);
            statement.setTimestamp(2, Timestamp.valueOf(firstDay));
            statement.setTimestamp(3, Timestamp.valueOf(firstDayOfNextMonth));
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                names.add(resultSet.getString(1) + " " + resultSet.getString(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return names;
    }

    @Override
    public void save(User user) {
        jdbcTemplate.update("CALL insertUser(?, ?, ?)", user.getName(), user.getSurname(), user.getBirthdate());
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
