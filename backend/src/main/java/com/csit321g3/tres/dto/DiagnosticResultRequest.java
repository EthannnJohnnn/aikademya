package com.csit321g3.tres.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record DiagnosticResultRequest(
        @NotNull(message = "Attempt ID is required") Integer attemptId,
        @NotNull(message = "Total score is required") BigDecimal totalScore) {
}
