package com.csit321g3.tres.repository;

import com.csit321g3.tres.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    boolean existsByModuleId(Long moduleId);
}