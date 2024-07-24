package com.sleepkqq.taskmanagement.service;

import com.sleepkqq.taskmanagement.model.ChatMessage;
import com.sleepkqq.taskmanagement.model.enums.ChatMessageType;
import com.sleepkqq.taskmanagement.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;

    public ChatMessage createChatMessage(String creatorUsername, String content, ChatMessageType messageType) {
        var creator = userService.loadUserByUsername(creatorUsername);
        var chatMessage = ChatMessage.builder()
                .creator(creator)
                .content(content)
                .messageType(messageType)
                .build();

        return chatMessageRepository.save(chatMessage);
    }

    public ChatMessage getChatMessage(long id) {
        return chatMessageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Chat Message '" + id + "' not found"));
    }

    public List<ChatMessage> getAllChatMessages() {
        return chatMessageRepository.findAll();
    }

    public void deleteChatMessage(long id) {
        chatMessageRepository.deleteById(id);
    }

}
