package com.sleepkqq.taskmanagement.dto;

import com.sleepkqq.taskmanagement.model.Task;

import java.time.LocalDateTime;

public record TaskCreateResponse(
        Long id, String title, String description, String status, String priority,
        LocalDateTime createdDate, LocalDateTime updatedDate, LocalDateTime dueDate, String assignee, String reporter) {

    public static TaskCreateResponse fromTask(Task task) {
        return new TaskCreateResponse(
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
