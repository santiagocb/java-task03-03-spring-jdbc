package com.tuspring.dao;

import com.tuspring.dto.Like;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LikeDAOImpl implements LikeDAO {

    private final JdbcTemplate jdbcTemplate;

    public LikeDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Like like) {
        String sql = "INSERT INTO Likes (postid, userid, timestamp) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, like.getPostId(), like.getUserId(), like.getTimestamp());
    }

    @Override
    public List<Like> findByPostId(long postId) {
        String sql = "SELECT * FROM Likes WHERE postid = ?";
        return jdbcTemplate.query(sql, new LikeRowMapper(), postId);
    }

    @Override
    public void deleteByPostId(long postId) {
        String sql = "DELETE FROM Likes WHERE postid = ?";
        jdbcTemplate.update(sql, postId);
    }

    private static class LikeRowMapper implements RowMapper<Like> {
        @Override
        public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Like(
                    rs.getLong("postid"),
                    rs.getLong("userid"),
                    rs.getTimestamp("timestamp")
            );
        }
    }
}
