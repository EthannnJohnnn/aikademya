package com.csit321g3.tres.dto;

import jakarta.validation.constraints.NotBlank;

public record CompetencyRequest(
    @NotBlank(message = "Competency name is required")
    String name
) {}
