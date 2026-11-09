package com.csit321g3.tres.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Integer questionId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "assessment_id", nullable = false)
    private Assessment assessment;

    // Temporary scalar until Competency is implemented by its assigned developer.
    // ERD calls this competency_id a foreign key; a database FK is not enforced yet.
    @Column(name = "competency_id", nullable = false)
    private Integer competencyId;

    public Question() {}

    public Integer getQuestionId() { return questionId; }
    public void setQuestionId(Integer value) { this.questionId = value; }
    public Assessment getAssessment() { return assessment; }
    public void setAssessment(Assessment value) { this.assessment = value; }
    public Integer getCompetencyId() { return competencyId; }
    public void setCompetencyId(Integer value) { this.competencyId = value; }
}
