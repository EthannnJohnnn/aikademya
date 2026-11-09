package com.csit321g3.tres.dto;

import jakarta.validation.constraints.*;

public record ResponseRequest(
        @NotNull(message = "Attempt ID is required") Integer attemptId,
        @NotNull(message = "Question ID is required") Integer questionId) {
}
