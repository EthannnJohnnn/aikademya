package com.csit321g3.tres.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "modules")
public class LearningModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    private String competency;

    @Column(length = 2000)
    private String description;

    public LearningModule() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCompetency() { return competency; }
    public void setCompetency(String competency) { this.competency = competency; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}