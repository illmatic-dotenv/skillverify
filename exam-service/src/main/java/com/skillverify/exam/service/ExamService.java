package com.skillverify.exam.service;

import com.skillverify.exam.model.*;
import com.skillverify.exam.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final AttemptRepository attemptRepository;
    private final BadgeRepository badgeRepository;

    public ExamService(ExamRepository examRepository,
                       QuestionRepository questionRepository,
                       AttemptRepository attemptRepository,
                       BadgeRepository badgeRepository) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
        this.attemptRepository = attemptRepository;
        this.badgeRepository = badgeRepository;
    }

    // Company creates an exam with questions
    public Map<String, String> createExam(Exam exam, List<Question> questions) {
        examRepository.save(exam);
        Exam saved = examRepository.findAll()
                .stream()
                .filter(e -> e.getTitle().equals(exam.getTitle()))
                .findFirst()
                .orElseThrow();
        for (Question q : questions) {
            q.setExamId(saved.getId());
            questionRepository.save(q);
        }
        return Map.of("message", "Exam created successfully");
    }

    // Get all exams
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    // Get questions for an exam (without correct answers)
    public List<Question> getQuestionsForExam(Long examId) {
        List<Question> questions = questionRepository.findByExamId(examId);
        // Hide correct answers from freelancer
        questions.forEach(q -> q.setCorrectOption(null));
        return questions;
    }

    // Freelancer submits answers
    public Map<String, Object> submitAttempt(Long examId, String username, Map<Long, String> answers) {
        List<Question> questions = questionRepository.findByExamId(examId);
        Exam exam = examRepository.findById(examId).orElseThrow();

        int score = 0;
        for (Question q : questions) {
            String submitted = answers.get(q.getId());
            if (submitted != null && submitted.equalsIgnoreCase(q.getCorrectOption())) {
                score++;
            }
        }

        boolean passed = score >= exam.getPassScore();

        Attempt attempt = new Attempt();
        attempt.setExamId(examId);
        attempt.setUsername(username);
        attempt.setScore(score);
        attempt.setPassed(passed);
        attemptRepository.save(attempt);

        if (passed) {
            Badge badge = new Badge();
            badge.setUsername(username);
            badge.setExamTitle(exam.getTitle());
            badgeRepository.save(badge);
        }

        return Map.of(
            "score", score,
            "passed", passed,
            "message", passed ? "Congratulations! Badge issued." : "Better luck next time."
        );
    }

    // Get badges for a freelancer
    public List<Badge> getBadges(String username) {
        return badgeRepository.findByUsername(username);
    }

    // Get attempts for a freelancer
    public List<Attempt> getAttempts(String username) {
        return attemptRepository.findByUsername(username);
    }
}
