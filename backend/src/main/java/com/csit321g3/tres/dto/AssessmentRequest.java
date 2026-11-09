package com.csit321g3.tres.dto;

import jakarta.validation.constraints.*;

public record AssessmentRequest(
        @NotBlank(message = "Title is required") String title) {
}
