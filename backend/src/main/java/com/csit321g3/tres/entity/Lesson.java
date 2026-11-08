package com.csit321g3.tres.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "module_id", nullable = false)
    private LearningModule module;

    @Column(nullable = false)
    private String title;

    @Column(length = 5000)
    private String content;

    public Lesson() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LearningModule getModule() { return module; }
    public void setModule(LearningModule module) { this.module = module; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}