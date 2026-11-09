package com.csit321g3.tres.repository;

import com.csit321g3.tres.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepository extends JpaRepository<Assessment, Integer> {
    boolean existsByTitleIgnoreCase(String title);
}
