package com.sleepkqq.taskmanagement.dto.responses;

import com.sleepkqq.taskmanagement.model.Task;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Task Response")
public record TaskResponse(
        @Schema(description = "Task ID", example = "1")
        Long id,
        @Schema(description = "Title of the task", example = "Implement login feature")
        String title,
        @Schema(description = "Description of the task", example = "Implement the user login feature with JWT authentication")
        String description,
        @Schema(description = "Status of the task", example = "IN_PROGRESS")
        String status,
        @Schema(description = "Priority of the task", example = "HIGH")
        String priority,
        @Schema(description = "Task creation date and time", example = "2023-07-15T10:00:00")
        LocalDateTime createdDate,
        @Schema(description = "Task last updated date and time", example = "2023-07-15T12:00:00")
        LocalDateTime updatedDate,
        @Schema(description = "Task due date and time", example = "2023-07-20T10:00:00")
        LocalDateTime dueDate,
        @Schema(description = "Username of the assignee", example = "johndoe")
        String assignee,
        @Schema(description = "Username of the reporter", example = "johndoe2")
        String reporter) {

    public static TaskResponse fromTask(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getPriority().name(),
                task.getCreatedDate(),
                task.getUpdatedDate(),
                task.getDueDate(),
                task.getAssignee().getUsername(),
                task.getReporter().getUsername());
    }

}
