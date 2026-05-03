package com.skillverify.exam.repository;

import com.skillverify.exam.model.Exam;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ExamRepository {

    private final JdbcTemplate jdbcTemplate;

    public ExamRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Exam> examRowMapper = (rs, rowNum) -> {
        Exam exam = new Exam();
        exam.setId(rs.getLong("id"));
        exam.setTitle(rs.getString("title"));
        exam.setDescription(rs.getString("description"));
        exam.setCreatedBy(rs.getString("created_by"));
        exam.setPassScore(rs.getInt("pass_score"));
        return exam;
    };

    public void save(Exam exam) {
        String sql = "INSERT INTO exams (title, description, created_by, pass_score) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, exam.getTitle(), exam.getDescription(), exam.getCreatedBy(), exam.getPassScore());
    }

    public List<Exam> findAll() {
        return jdbcTemplate.query("SELECT * FROM exams", examRowMapper);
    }

    public Optional<Exam> findById(Long id) {
        String sql = "SELECT * FROM exams WHERE id = ?";
        return jdbcTemplate.query(sql, examRowMapper, id).stream().findFirst();
    }
}
