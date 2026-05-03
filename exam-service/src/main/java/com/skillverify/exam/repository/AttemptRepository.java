package com.skillverify.exam.repository;

import com.skillverify.exam.model.Attempt;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AttemptRepository {

    private final JdbcTemplate jdbcTemplate;

    public AttemptRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Attempt> attemptRowMapper = (rs, rowNum) -> {
        Attempt a = new Attempt();
        a.setId(rs.getLong("id"));
        a.setExamId(rs.getLong("exam_id"));
        a.setUsername(rs.getString("username"));
        a.setScore(rs.getInt("score"));
        a.setPassed(rs.getBoolean("passed"));
        a.setAttemptedAt(rs.getTimestamp("attempted_at").toLocalDateTime());
        return a;
    };

    public void save(Attempt attempt) {
        String sql = "INSERT INTO attempts (exam_id, username, score, passed) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, attempt.getExamId(), attempt.getUsername(),
                attempt.getScore(), attempt.isPassed());
    }

    public List<Attempt> findByUsername(String username) {
        String sql = "SELECT * FROM attempts WHERE username = ?";
        return jdbcTemplate.query(sql, attemptRowMapper, username);
    }
}
