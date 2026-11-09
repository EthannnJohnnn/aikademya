package com.csit321g3.tres.repository;

import com.csit321g3.tres.entity.CompetencyScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetencyScoreRepository
        extends JpaRepository<CompetencyScore, Integer> {

    boolean existsByCompetency_CompetencyId(Integer competencyId);

    boolean existsByResult_ResultId(Integer resultId);
}
