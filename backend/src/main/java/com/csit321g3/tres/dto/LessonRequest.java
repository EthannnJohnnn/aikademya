package com.csit321g3.tres.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LessonRequest(
        @NotNull Long moduleId,
        @NotBlank String title) {
}