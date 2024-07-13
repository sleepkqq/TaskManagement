package com.sleepkqq.taskmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Registration Request")
public record SignUpRequest(
        @Schema(description = "Username", example = "Jon")
        @Size(min = 5, max = 50, message = "Username must be between 5 and 50 characters")
        @NotBlank(message = "Username cannot be empty")
        String username,
        @Schema(description = "Email Address", example = "jondoe@gmail.com")
        @Size(min = 5, max = 255, message = "Email address must be between 5 and 255 characters")
        @NotBlank(message = "Email address cannot be empty")
        @Email(message = "Email address must be in the format user@example.com")
        String email,
        @Schema(description = "Password", example = "my_1secret1_password")
        @Size(max = 255, message = "Password length must not exceed 255 characters")
        String password) {
}