package com.sleepkqq.taskmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response with Access Token")
public record JwtAuthenticationResponse(
        @Schema(description = "Access Token", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyMjUwNj...")
        String token) {
}