package com.skillverify.exam.controller;

import com.skillverify.exam.model.*;
import com.skillverify.exam.service.ExamService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exams")
@CrossOrigin(origins = "http://localhost:3000")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    // Company posts a new exam
    @PostMapping("/create")
    public ResponseEntity<?> createExam(@RequestBody Map<String, Object> request,
                                         Authentication auth) {
        Exam exam = new Exam();
        exam.setTitle((String) request.get("title"));
        exam.setDescription((String) request.get("description"));
        exam.setCreatedBy(auth.getName());
        exam.setPassScore((Integer) request.get("passScore"));

        List<Question> questions = ((List<Map<String, String>>) request.get("questions"))
            .stream().map(q -> {
                Question question = new Question();
                question.setQuestionText(q.get("questionText"));
                question.setOptionA(q.get("optionA"));
                question.setOptionB(q.get("optionB"));
                question.setOptionC(q.get("optionC"));
                question.setOptionD(q.get("optionD"));
                question.setCorrectOption(q.get("correctOption"));
                return question;
            }).toList();

        return ResponseEntity.ok(examService.createExam(exam, questions));
    }

    // Get all exams
    @GetMapping
    public ResponseEntity<?> getAllExams() {
        return ResponseEntity.ok(examService.getAllExams());
    }

    // Get questions for an exam
    @GetMapping("/{examId}/questions")
    public ResponseEntity<?> getQuestions(@PathVariable Long examId) {
        return ResponseEntity.ok(examService.getQuestionsForExam(examId));
    }

    // Freelancer submits answers
    @PostMapping("/{examId}/submit")
    public ResponseEntity<?> submitAttempt(@PathVariable Long examId,
                                            @RequestBody Map<Long, String> answers,
                                            Authentication auth) {
        return ResponseEntity.ok(examService.submitAttempt(examId, auth.getName(), answers));
    }

    // Get my badges
    @GetMapping("/badges")
    public ResponseEntity<?> getBadges(Authentication auth) {
        return ResponseEntity.ok(examService.getBadges(auth.getName()));
    }

    // Get my attempts
    @GetMapping("/attempts")
    public ResponseEntity<?> getAttempts(Authentication auth) {
        return ResponseEntity.ok(examService.getAttempts(auth.getName()));
    }
}
