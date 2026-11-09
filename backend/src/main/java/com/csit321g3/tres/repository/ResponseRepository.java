package com.csit321g3.tres.repository;

import com.csit321g3.tres.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Response, Integer> {
    boolean existsByAttempt_AttemptId(Integer attemptId);
    boolean existsByQuestion_QuestionId(Integer questionId);
    boolean existsByAttempt_AttemptIdAndQuestion_QuestionId(Integer attemptId, Integer questionId);
}
