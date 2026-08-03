package com.example.SB_Thymeleaf_01.Controller;


import com.example.SB_Thymeleaf_01.ChatMessage;
import com.example.SB_Thymeleaf_01.Service.ChatService;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat.SendMessage")
    public ChatMessage SendMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = (String) headerAccessor.getSessionAttributes().get("sessionId");
        if(sessionId == null) {
            sessionId = UUID.randomUUID().toString();
            headerAccessor.getSessionAttributes().put("sessionId", sessionId);
        }
        chatMessage.setSessionId(sessionId);
        return chatService.sendMessage(chatMessage);
    }

    @MessageMapping("/chat.addUser")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = UUID.randomUUID().toString();
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSenderName());
        headerAccessor.getSessionAttributes().put("sessionId", sessionId);

        chatMessage.setType(ChatMessage.MessageType.JOIN);
        chatMessage.setSessionId(sessionId);
        chatService.sendMessage(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat.typing")
    public void typing(@Payload boolean isTyping, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = (String) headerAccessor.getSessionAttributes().get("sessionId");
        chatService.userTyping(sessionId, isTyping);
    }

}
