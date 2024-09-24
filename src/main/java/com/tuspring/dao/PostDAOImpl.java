package com.tuspring.dao;

import com.tuspring.dto.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PostDAOImpl implements PostDAO {

    private final JdbcTemplate jdbcTemplate;

    public PostDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Post post) {
        String sql = "INSERT INTO Posts (userId, text, timestamp) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, post.getUserId(), post.getText(), post.getTimestamp());
    }

    @Override
    public List<Post> findByUserId(long userId) {
        String sql = "SELECT * FROM Posts WHERE userId = ?";
        return jdbcTemplate.query(sql, new PostRowMapper(), userId);
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM Posts WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class PostRowMapper implements RowMapper<Post> {
        @Override
        public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Post(
                    rs.getLong("userId"),
                    rs.getString("text"),
                    rs.getTimestamp("timestamp")
            );
        }
    }
}
