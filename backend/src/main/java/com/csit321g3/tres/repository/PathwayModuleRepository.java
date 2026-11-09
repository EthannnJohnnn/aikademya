package com.csit321g3.tres.repository;

import com.csit321g3.tres.entity.PathwayModule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PathwayModuleRepository extends JpaRepository<PathwayModule, Integer> {

    boolean existsByPathway_PathwayIdAndModule_Id(Integer pathwayId, Long moduleId);

    boolean existsByPathway_PathwayId(Integer pathwayId);

    boolean existsByModule_Id(Long moduleId);
}