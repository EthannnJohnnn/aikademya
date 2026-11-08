package com.csit321g3.tres.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SchoolRequest(

    @NotBlank(message = "School name is required")
    String schoolName,

    @NotNull(message = "Division ID is required")
    Integer divisionId

) {}
