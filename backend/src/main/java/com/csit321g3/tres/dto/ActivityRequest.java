package com.csit321g3.tres.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActivityRequest(
        @NotNull Long lessonId,
        @NotBlank String title,
        String instructions) {
}