package com.csit321g3.tres.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "assessment_attempts")
public class AssessmentAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attempt_id")
    private Integer attemptId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "assessment_id", nullable = false)
    private Assessment assessment;

    public AssessmentAttempt() {}

    public Integer getAttemptId() { return attemptId; }
    public void setAttemptId(Integer value) { this.attemptId = value; }
    public User getUser() { return user; }
    public void setUser(User value) { this.user = value; }
    public Assessment getAssessment() { return assessment; }
    public void setAssessment(Assessment value) { this.assessment = value; }
}
