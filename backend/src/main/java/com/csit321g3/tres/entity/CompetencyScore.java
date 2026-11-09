package com.csit321g3.tres.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "competency_scores")
public class CompetencyScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id")
    private Integer scoreId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "result_id", nullable = false)
    private DiagnosticResult result;

    @ManyToOne(optional = false)
    @JoinColumn(name = "competency_id", nullable = false)
    private Competency competency;

    public CompetencyScore() {}

    public Integer getScoreId() {
        return scoreId;
    }

    public void setScoreId(Integer scoreId) {
        this.scoreId = scoreId;
    }

    public DiagnosticResult getResult() {
        return result;
    }

    public void setResult(DiagnosticResult result) {
        this.result = result;
    }

    public Competency getCompetency() {
        return competency;
    }

    public void setCompetency(Competency competency) {
        this.competency = competency;
    }
}
