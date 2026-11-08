package com.csit321g3.tres.repository;

import com.csit321g3.tres.entity.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
    boolean existsByLessonId(Long lessonId);
}