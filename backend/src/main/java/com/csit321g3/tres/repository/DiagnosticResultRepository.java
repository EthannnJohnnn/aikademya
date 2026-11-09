package com.csit321g3.tres.repository;

import com.csit321g3.tres.entity.DiagnosticResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosticResultRepository extends JpaRepository<DiagnosticResult, Integer> {
    boolean existsByAttempt_AttemptId(Integer attemptId);
}
