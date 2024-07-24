package com.sleepkqq.taskmanagement.controller;

import com.sleepkqq.taskmanagement.dto.requests.ChatMessageCreateRequest;
import com.sleepkqq.taskmanagement.dto.responses.ChatMessageResponse;
import com.sleepkqq.taskmanagement.service.ChatService;
import com.sleepkqq.taskmanagement.utils.ChatMessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/send-message")
    @SendTo("/topic/public")
    public ResponseEntity<ChatMessageResponse> sendMessage(@RequestBody ChatMessageCreateRequest request) {
        var createdChatMessage = chatService.createChatMessage(
                request.creatorUsername(),
                request.content(),
                request.messageType()
        );
        return ResponseEntity.status(CREATED).body(ChatMessageResponse.fromChatMessage(createdChatMessage));
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<ChatMessageResponse> getChatMessage(@PathVariable long id) {
        return ResponseEntity.ok(ChatMessageResponse.fromChatMessage(chatService.getChatMessage(id)));
    }

    @GetMapping("/messages/get/all")
    public ResponseEntity<List<ChatMessageResponse>> getChatMessage() {
        return ResponseEntity.ok(ChatMessageUtils.convertTasksToResponse(chatService.getAllChatMessages()));
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<ChatMessageResponse> deleteChatMessage(@PathVariable long id) {
        chatService.deleteChatMessage(id);
        return ResponseEntity.noContent().build();
    }

}
