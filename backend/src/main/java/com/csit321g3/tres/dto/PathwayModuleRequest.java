package com.csit321g3.tres.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PathwayModuleRequest(
        @NotNull(message = "Pathway ID is required") Integer pathwayId,
        @NotNull(message = "Module ID is required") Long moduleId,
        @NotNull(message = "Sequence number is required") @Min(value = 1, message = "Sequence number must be at least 1") Integer sequenceNumber) {
}