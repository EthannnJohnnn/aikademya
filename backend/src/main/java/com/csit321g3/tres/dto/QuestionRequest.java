package com.csit321g3.tres.dto;

import jakarta.validation.constraints.*;

public record QuestionRequest(
        @NotNull(message = "Assessment ID is required") Integer assessmentId,
        @NotNull(message = "Competency ID is required") @Min(1) Integer competencyId) {
}
