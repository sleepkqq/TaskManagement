package com.sleepkqq.taskmanagement.dto.requests;

import com.sleepkqq.taskmanagement.model.enums.TaskPriority;
import com.sleepkqq.taskmanagement.model.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Schema(description = "Task Create Request")
public record TaskCreateRequest(
        @Schema(description = "Title of the task", example = "Implement login feature")
        @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
        @NotBlank(message = "Title cannot be empty")
        String title,
        @Schema(description = "Description of the task", example = "Implement the user login feature with JWT authentication")
        @Size(max = 500, message = "Description length must not exceed 500 characters")
        @NotBlank(message = "Description cannot be empty")
        String description,
        @Schema(description = "Username of the assignee", example = "johndoe")
        @Size(min = 5, max = 50, message = "Assignee username must be between 5 and 50 characters")
        @NotBlank(message = "Assignee cannot be empty")
        String assignee,
        @Schema(description = "Username of the reporter", example = "johndoe2")
        @Size(min = 5, max = 50, message = "Reporter username must be between 5 and 50 characters")
        @NotBlank(message = "Reporter cannot be empty")
        String reporter,
        @Schema(description = "Status of the task", example = "TO_DO")
        @NotNull(message = "Status cannot be null")
        TaskStatus status,
        @Schema(description = "Priority of the task", example = "HIGH")
        @NotNull(message = "Priority cannot be null")
        TaskPriority priority,
        @Schema(description = "Due date of the task", example = "2024-12-31T23:59:59")
        @FutureOrPresent(message = "Due date must be in the future or present")
        LocalDateTime dueDate) {
}
