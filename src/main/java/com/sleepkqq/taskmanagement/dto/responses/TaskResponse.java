package com.sleepkqq.taskmanagement.dto.responses;

import com.sleepkqq.taskmanagement.model.Task;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id, String title, String description, String status, String priority,
        LocalDateTime createdDate, LocalDateTime updatedDate, LocalDateTime dueDate, String assignee, String reporter) {

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
