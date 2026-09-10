package com.example.SB_Thymeleaf_01.Service;

import com.example.SB_Thymeleaf_01.Converters.ChatHeaderFEMapper;
import com.example.SB_Thymeleaf_01.DTO.ChatHeaderFE;
import com.example.SB_Thymeleaf_01.Models.ChatMessage;
import com.example.SB_Thymeleaf_01.Repositories.ChatMessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public List<ChatHeaderFE> getAllConversationsForChatHeaders() {
        //updating SENT messages to DELIVERED sent by Customers
        int updatedIdAsDelivered = chatMessageRepository.updateSentMessagestoDeliveredByAdmin();
        System.out.println("SENT status messages has been turned to DELIVERED which are : " + updatedIdAsDelivered);

        List<ChatMessage> chatMessages = chatMessageRepository.returnConversationsForChatHeader();
        return chatMessages.stream()
                .map(ChatHeaderFEMapper::toDto)
                .collect(Collectors.toList()); //Returning all the messages of repo
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

