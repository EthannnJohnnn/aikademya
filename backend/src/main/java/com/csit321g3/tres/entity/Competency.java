package com.csit321g3.tres.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "competencies")
public class Competency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competency_id")
    private Integer competencyId;

    @Column(name = "name", nullable = false)
    private String name;

    public Competency() {}

    public Integer getCompetencyId() {
        return competencyId;
    }

    public void setCompetencyId(Integer competencyId) {
        this.competencyId = competencyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
