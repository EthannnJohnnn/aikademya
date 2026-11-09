
package com.csit321g3.tres.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "diagnostic_results")
public class DiagnosticResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private Integer resultId;

    public DiagnosticResult() {
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }
}
