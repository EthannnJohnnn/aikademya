package com.csit321g3.tres.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CertificateRequest(
        @NotNull(message = "User ID is required") Integer userId,
        @NotBlank(message = "Verification code is required") String verificationCode) {
}