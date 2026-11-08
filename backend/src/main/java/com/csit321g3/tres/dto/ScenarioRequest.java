package com.csit321g3.tres.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ScenarioRequest(
        @NotNull Long lessonId,
        @NotBlank String situation) {
}