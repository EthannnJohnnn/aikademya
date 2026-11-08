package com.csit321g3.tres.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "pathway_modules",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_pathway_module",
        columnNames = {"pathway_id", "module_id"}
    )
)
public class PathwayModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pathway_module_id")
    private Integer pathwayModuleId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pathway_id", nullable = false)
    private LearningPathway pathway;

    @ManyToOne(optional = false)
    @JoinColumn(name = "module_id", nullable = false)
    private LearningModule module;

    @Column(name = "sequence_number", nullable = false)
    private Integer sequenceNumber;

    public PathwayModule() {}

    public Integer getPathwayModuleId() { return pathwayModuleId; }
    public void setPathwayModuleId(Integer pathwayModuleId) { this.pathwayModuleId = pathwayModuleId; }
    public LearningPathway getPathway() { return pathway; }
    public void setPathway(LearningPathway pathway) { this.pathway = pathway; }
    public LearningModule getModule() { return module; }
    public void setModule(LearningModule module) { this.module = module; }
    public Integer getSequenceNumber() { return sequenceNumber; }
    public void setSequenceNumber(Integer sequenceNumber) { this.sequenceNumber = sequenceNumber; }
}