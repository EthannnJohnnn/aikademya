package com.csit321g3.tres.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "assessments")
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assessment_id")
    private Integer assessmentId;

    @Column(name = "title", nullable = false)
    private String title;

    public Assessment() {}

    public Integer getAssessmentId() { return assessmentId; }
    public void setAssessmentId(Integer value) { this.assessmentId = value; }
    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }
}
