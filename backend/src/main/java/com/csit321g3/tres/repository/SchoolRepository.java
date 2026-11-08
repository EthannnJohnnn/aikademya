package com.csit321g3.tres.repository;

import com.csit321g3.tres.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository
        extends JpaRepository<School, Integer> {
}
