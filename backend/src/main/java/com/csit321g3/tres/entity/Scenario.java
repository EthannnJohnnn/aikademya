package com.csit321g3.tres.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "scenarios")
public class Scenario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    @Column(nullable = false, length = 2000)
    private String question;

    @Column(length = 2000)
    private String options;

    public Scenario() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Activity getActivity() { return activity; }
    public void setActivity(Activity activity) { this.activity = activity; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public String getOptions() { return options; }
    public void setOptions(String options) { this.options = options; }
}