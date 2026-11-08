package com.csit321g3.tres.dto;

import jakarta.validation.constraints.NotBlank;

public record DivisionRequest(
    @NotBlank(message = "Division name is required")
    String name
) {}
