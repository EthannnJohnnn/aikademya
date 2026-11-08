package com.csit321g3.tres.dto;

public record UserResponse(
    Integer userId,
    String email,
    Integer schoolId
) {}
