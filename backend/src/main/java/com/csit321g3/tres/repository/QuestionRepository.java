package com.csit321g3.tres.repository;

import com.csit321g3.tres.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    boolean existsByCompetencyId(Integer competencyId);

    boolean existsByAssessment_AssessmentId(Integer assessmentId);
}
