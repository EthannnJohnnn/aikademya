package com.csit321g3.tres.dto;

import jakarta.validation.constraints.NotNull;

public record UserRoleRequest(

    @NotNull(message = "User ID is required")
    Integer userId,

    @NotNull(message = "Role ID is required")
    Integer roleId

) {}
