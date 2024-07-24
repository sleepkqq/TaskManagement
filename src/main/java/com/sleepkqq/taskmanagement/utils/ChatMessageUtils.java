package com.sleepkqq.taskmanagement.utils;

import com.sleepkqq.taskmanagement.dto.responses.ChatMessageResponse;
import com.sleepkqq.taskmanagement.model.ChatMessage;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ChatMessageUtils {

    public static List<ChatMessageResponse> convertTasksToResponse(List<ChatMessage> chatMessages) {
        return chatMessages.stream().map(ChatMessageResponse::fromChatMessage).toList();
    }

}
