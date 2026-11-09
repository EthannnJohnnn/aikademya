package com.csit321g3.tres.dto;

import jakarta.validation.constraints.NotNull;

public record CompetencyScoreRequest(
    @NotNull(message = "Result ID is required")
    Integer resultId,

    @NotNull(message = "Competency ID is required")
    Integer competencyId
) {}
