package com.skillverify.exam.repository;

import com.skillverify.exam.model.Badge;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BadgeRepository {

    private final JdbcTemplate jdbcTemplate;

    public BadgeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Badge> badgeRowMapper = (rs, rowNum) -> {
        Badge b = new Badge();
        b.setId(rs.getLong("id"));
        b.setUsername(rs.getString("username"));
        b.setExamTitle(rs.getString("exam_title"));
        b.setIssuedAt(rs.getTimestamp("issued_at").toLocalDateTime());
        return b;
    };

    public void save(Badge badge) {
        String sql = "INSERT INTO badges (username, exam_title) VALUES (?, ?)";
        jdbcTemplate.update(sql, badge.getUsername(), badge.getExamTitle());
    }

    public List<Badge> findByUsername(String username) {
        String sql = "SELECT * FROM badges WHERE username = ?";
        return jdbcTemplate.query(sql, badgeRowMapper, username);
    }
}
