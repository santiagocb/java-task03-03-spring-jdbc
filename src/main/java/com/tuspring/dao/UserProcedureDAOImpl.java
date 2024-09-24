package com.tuspring.dao;

import com.tuspring.dto.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@Qualifier("userDAO2")
public class UserProcedureDAOImpl implements UserDAO {

    private final JdbcTemplate jdbcTemplate;

    public UserProcedureDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> findUserNamesWithMoreThanFriendsNumberAnd100LikesInTheLasthMonth(long friendShips) {

        var currentDate = LocalDate.now();
        var firstDay = currentDate.withDayOfMonth(1);
        var firstDayOfNextMonth = currentDate.plusMonths(1);

        return jdbcTemplate.execute((Connection con) -> {
            List<String> fullNames = new ArrayList<>();

            try (CallableStatement callableStatement = con.prepareCall("{CALL selectFullnamesWithFriendsAndLikes(?, ?, ?, ?)}")) {

                // Set the input parameters
                callableStatement.setLong(1, friendShips);
                callableStatement.setDate(2, Date.valueOf(firstDay));
                callableStatement.setDate(3, Date.valueOf(firstDayOfNextMonth));

                // Register the OUT parameter for the cursor
                callableStatement.registerOutParameter(4, Types.OTHER);

                // Execute the stored procedure
                callableStatement.execute();

                // Get the cursor from the OUT parameter
                try (ResultSet rs = (ResultSet) callableStatement.getObject(4)) {
                    // Iterate through the result set and collect the full names
                    while (rs.next()) {
                        String fullName = rs.getString("fullname");
                        fullNames.add(fullName);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace(); // Handle SQL exceptions
            }

            return fullNames;
        });
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
