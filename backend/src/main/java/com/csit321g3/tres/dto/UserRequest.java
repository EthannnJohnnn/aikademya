package com.csit321g3.tres.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email,

    Integer schoolId

) {}
