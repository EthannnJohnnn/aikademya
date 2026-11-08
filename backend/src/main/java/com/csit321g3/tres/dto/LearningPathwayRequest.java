package com.csit321g3.tres.dto;

import jakarta.validation.constraints.NotNull;

public record LearningPathwayRequest(
        @NotNull(message = "User ID is required") Integer userId) {
}