package com.csit321g3.tres.repository;

import com.csit321g3.tres.entity.AssessmentAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentAttemptRepository extends JpaRepository<AssessmentAttempt, Integer> {
    boolean existsByAssessment_AssessmentId(Integer assessmentId);
}
