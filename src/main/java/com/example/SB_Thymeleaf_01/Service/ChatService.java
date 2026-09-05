package com.example.SB_Thymeleaf_01.Service;

import com.example.SB_Thymeleaf_01.Models.ChatMessage;
import com.example.SB_Thymeleaf_01.Repositories.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public ChatMessage sendMessage(ChatMessage message) {
        message.setTimestamp(LocalDateTime.now());

        ChatMessage savedMessage = chatMessageRepository.save(message);

        simpMessagingTemplate.convertAndSendToUser(
                message.getReceiverId(),
                "queue/messages",
                savedMessage
        );

        return savedMessage;
    }

    public List<ChatMessage> getConversation(String userId, String adminId) {

        return chatMessageRepository.findConversation(userId, adminId);
    }

    public void markAsRead(Long messageId) {
        ChatMessage message = chatMessageRepository.findById(messageId).orElse(null);
        if(message != null) {
            chatMessageRepository.save(message);
        }
    }

    public void userTyping(String sessionId, boolean isTyping){
        simpMessagingTemplate.convertAndSend("topic/typing" + sessionId, isTyping);
    }
}

