package com.csit321g3.tres.repository;

import com.csit321g3.tres.entity.ModuleProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleProgressRepository
        extends JpaRepository<ModuleProgress, Integer> {

    boolean existsByModuleId(Long moduleId);
}