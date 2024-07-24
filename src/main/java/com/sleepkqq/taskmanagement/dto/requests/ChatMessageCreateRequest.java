package com.sleepkqq.taskmanagement.dto.requests;

import com.sleepkqq.taskmanagement.model.enums.ChatMessageType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Chat Message Create Request")
public record ChatMessageCreateRequest(
        @Schema(description = "Creator Username", example = "Jon")
        @Size(min = 5, max = 50, message = "Username must be between 5 and 50 characters")
        @NotBlank(message = "Creator Username cannot be empty")
        String creatorUsername,
        @Schema(description = "Content of the message", example = "Hello!")
        @Size(max = 500, message = "Content length must not exceed 500 characters")
        @NotBlank(message = "Content cannot be empty")
        String content,
        @Schema(description = "Type of the message", example = "CHAT")
        @NotNull(message = "Chat Message Type cannot be null")
        ChatMessageType messageType) {
}
