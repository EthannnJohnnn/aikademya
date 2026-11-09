package com.csit321g3.tres.dto;

import jakarta.validation.constraints.*;

public record AssessmentAttemptRequest(
        @NotNull(message = "User ID is required") Integer userId,
        @NotNull(message = "Assessment ID is required") Integer assessmentId) {
}
