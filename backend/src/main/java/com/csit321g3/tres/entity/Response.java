package com.csit321g3.tres.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "responses")
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id")
    private Integer responseId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "attempt_id", nullable = false)
    private AssessmentAttempt attempt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    public Response() {}

    public Integer getResponseId() { return responseId; }
    public void setResponseId(Integer value) { this.responseId = value; }
    public AssessmentAttempt getAttempt() { return attempt; }
    public void setAttempt(AssessmentAttempt value) { this.attempt = value; }
    public Question getQuestion() { return question; }
    public void setQuestion(Question value) { this.question = value; }
}
