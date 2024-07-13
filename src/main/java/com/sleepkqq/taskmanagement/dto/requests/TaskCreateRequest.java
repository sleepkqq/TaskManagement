package com.sleepkqq.taskmanagement.dto.requests;

public record TaskCreateRequest(String title, String description, String assignee, String reporter) {
}
