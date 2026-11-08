package com.csit321g3.tres.repository;

import com.csit321g3.tres.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    boolean existsByLessonId(Long lessonId);
}