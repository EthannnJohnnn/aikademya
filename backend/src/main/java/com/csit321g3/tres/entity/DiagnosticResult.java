package com.csit321g3.tres.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "diagnostic_results")
public class DiagnosticResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private Integer resultId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "attempt_id", nullable = false)
    private AssessmentAttempt attempt;

    @Column(name = "total_score", nullable = false)
    private java.math.BigDecimal totalScore;

    public DiagnosticResult() {}

    public Integer getResultId() { return resultId; }
    public void setResultId(Integer value) { this.resultId = value; }
    public AssessmentAttempt getAttempt() { return attempt; }
    public void setAttempt(AssessmentAttempt value) { this.attempt = value; }
    public java.math.BigDecimal getTotalScore() { return totalScore; }
    public void setTotalScore(java.math.BigDecimal value) { this.totalScore = value; }
}
