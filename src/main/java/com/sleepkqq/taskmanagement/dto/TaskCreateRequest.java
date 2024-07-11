package com.sleepkqq.taskmanagement.dto;

public record TaskCreateRequest(String title, String description, String assignee, String reporter) {
}
