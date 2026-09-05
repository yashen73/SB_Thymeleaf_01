package com.example.SB_Thymeleaf_01.Controller;

import com.example.SB_Thymeleaf_01.Models.ChatMessage;
import com.example.SB_Thymeleaf_01.Service.ChatService;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping
public class AdminChatController {

    @Autowired
    private ChatService chatService;


    @GetMapping("/login")
    public String login() {
        return "admin-login";
    }

    @GetMapping("/chat/{userId}")
    @ResponseBody
    public List<ChatMessage> getChatHistory(@PathVariable String sessionId) {
        System.out.println(chatService.getConversation(sessionId, "admin"));
        return chatService.getConversation(sessionId, "admin");
    }

    @PostMapping("/message/send")
    @ResponseBody
    public ChatMessage sendAdminMessage(@RequestBody ChatMessage message, Principal principal) {
        message.setSenderId("admin");
        message.setSenderName(principal.getName());
        message.setSessionId(principal.getName()+ UUID.randomUUID());
        return chatService.sendMessage(message);
    }

    @PostMapping("/meessage/read/{messageId}")
    @ResponseBody
    public void markAsRead(@PathVariable Long messageId) {
        chatService.markAsRead(messageId);
    }

    private List<String> getActiveChats() {
        return List.of();
    }

}
