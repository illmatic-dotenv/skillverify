package com.skillverify.exam.repository;

import com.skillverify.exam.model.Question;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionRepository {

    private final JdbcTemplate jdbcTemplate;

    public QuestionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Question> questionRowMapper = (rs, rowNum) -> {
        Question q = new Question();
        q.setId(rs.getLong("id"));
        q.setExamId(rs.getLong("exam_id"));
        q.setQuestionText(rs.getString("question_text"));
        q.setOptionA(rs.getString("option_a"));
        q.setOptionB(rs.getString("option_b"));
        q.setOptionC(rs.getString("option_c"));
        q.setOptionD(rs.getString("option_d"));
        q.setCorrectOption(rs.getString("correct_option"));
        return q;
    };

    public void save(Question question) {
        String sql = "INSERT INTO questions (exam_id, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, question.getExamId(), question.getQuestionText(),
                question.getOptionA(), question.getOptionB(),
                question.getOptionC(), question.getOptionD(),
                question.getCorrectOption());
    }

    public List<Question> findByExamId(Long examId) {
        String sql = "SELECT * FROM questions WHERE exam_id = ?";
        return jdbcTemplate.query(sql, questionRowMapper, examId);
    }
}