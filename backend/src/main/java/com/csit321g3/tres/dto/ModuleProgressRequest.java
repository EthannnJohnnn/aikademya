package com.csit321g3.tres.dto;

import jakarta.validation.constraints.NotNull;

public record ModuleProgressRequest(

    @NotNull(message = "User ID is required")
    Integer userId,

    @NotNull(message = "Module ID is required")
    Long moduleId

) {}
