package com.sleepkqq.taskmanagement.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Authentication Request")
public record SignInRequest(
        @Schema(description = "Username", example = "Jon")
        @Size(min = 5, max = 50, message = "Username must be between 5 and 50 characters")
        @NotBlank(message = "Username cannot be empty")
        String username,
        @Schema(description = "Password", example = "my_1secret1_password")
        @Size(max = 255, message = "Password length must not exceed 255 characters")
        @NotBlank(message = "Password cannot be empty")
         String password) {
}