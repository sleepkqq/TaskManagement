package com.sleepkqq.taskmanagement.dto.responses;

import com.sleepkqq.taskmanagement.model.ChatMessage;
import com.sleepkqq.taskmanagement.model.enums.ChatMessageType;
import io.swagger.v3.oas.annotations.media.Schema;

public record ChatMessageResponse(
        @Schema(description = "Creator Username", example = "Jon")
        String creatorUsername,
        @Schema(description = "Content of the message", example = "Hello!")
        String content,
        @Schema(description = "Type of the message", example = "CHAT")
        ChatMessageType messageType
) {

    public static ChatMessageResponse fromChatMessage(ChatMessage message) {
        return new ChatMessageResponse(
                message.getCreator().getUsername(),
                message.getContent(),
                message.getMessageType()
        );
    }

}
