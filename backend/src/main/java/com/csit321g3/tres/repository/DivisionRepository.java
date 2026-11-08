package com.csit321g3.tres.repository;

import com.csit321g3.tres.entity.Division;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DivisionRepository
        extends JpaRepository<Division, Integer> {
}
