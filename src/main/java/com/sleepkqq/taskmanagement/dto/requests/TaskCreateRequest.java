package com.sleepkqq.taskmanagement.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Task Create Request")
public record TaskCreateRequest(
        @Schema(description = "Title of the task", example = "Implement login feature")
        @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
        @NotBlank(message = "Title cannot be empty")
        String title,

        @Schema(description = "Description of the task", example = "Implement the user login feature with JWT authentication")
        @Size(max = 500, message = "Description length must not exceed 500 characters")
        String description,

        @Schema(description = "Username of the assignee", example = "johndoe")
        @Size(min = 5, max = 50, message = "Assignee username must be between 5 and 50 characters")
        @NotBlank(message = "Assignee cannot be empty")
        String assignee,

        @Schema(description = "Username of the reporter", example = "johndoe2")
        @Size(min = 5, max = 50, message = "Reporter username must be between 5 and 50 characters")
        @NotBlank(message = "Reporter cannot be empty")
        String reporter) {
}
