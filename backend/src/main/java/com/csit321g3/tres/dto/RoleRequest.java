package com.csit321g3.tres.dto;

import jakarta.validation.constraints.NotBlank;

public record RoleRequest(

    @NotBlank(message = "Role name is required")
    String roleName

) {}
