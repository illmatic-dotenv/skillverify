package com.skillverify.exam.model;

public class Exam {
    private Long id;
    private String title;
    private String description;
    private String createdBy;
    private int passScore;

    public Exam() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public int getPassScore() { return passScore; }
    public void setPassScore(int passScore) { this.passScore = passScore; }
}
