package com.tuspring.dao;

import com.tuspring.dto.Friendship;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FriendshipDAOImpl implements FriendshipDAO {

    private final JdbcTemplate jdbcTemplate;

    public FriendshipDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Friendship friendship) {
        String sql = "INSERT INTO Friendships (userid1, userid2, timestamp) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, friendship.getUserid1(), friendship.getUserid2(), friendship.getTimestamp());
    }

    @Override
    public List<Friendship> findByUserId(long userId) {
        String sql = "SELECT * FROM Friendships WHERE userid1 = ?";
        return jdbcTemplate.query(sql, new FriendshipRowMapper(), userId);
    }

    @Override
    public void deleteByUserId(long userId) {
        String sql = "DELETE FROM Friendships WHERE userid1 = ? OR userid2 = ?";
        jdbcTemplate.update(sql, userId, userId);
    }

    private static class FriendshipRowMapper implements RowMapper<Friendship> {
        @Override
        public Friendship mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Friendship(
                    rs.getLong("userid1"),
                    rs.getLong("userid2"),
                    rs.getTimestamp("timestamp")
            );
        }
    }
}
