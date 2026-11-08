package com.csit321g3.tres.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "learning_pathways")
public class LearningPathway {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pathway_id")
    private Integer pathwayId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public LearningPathway() {}

    public Integer getPathwayId() { return pathwayId; }
    public void setPathwayId(Integer pathwayId) { this.pathwayId = pathwayId; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}