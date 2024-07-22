package com.sleepkqq.taskmanagement.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthenticationResponse(
        @Schema(description = "Access Token", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYyMjUwNj...")
        String accessToken,
        @Schema(description = "Access Token Expiration", example = "900")
        int accessExpiresIn,
        @Schema(description = "Token Type", example = "Bearer")
        String tokenType) {
}
