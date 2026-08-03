package com.example.SB_Thymeleaf_01.Controller;

import com.example.SB_Thymeleaf_01.ChatMessage;
import com.example.SB_Thymeleaf_01.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class AdminChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/login")
    public String login() {
        return "admin-login";
    }

    @GetMapping("/chat/{sessionId}")
    @ResponseBody
    public List<ChatMessage> getChatHistory(@PathVariable String sessionId) {
        return chatService.getConversation(sessionId, "admin");
    }

    @PostMapping("/message/send")
    @ResponseBody
    public ChatMessage sendAdminMessage(@RequestBody ChatMessage message) {
        message.setSenderId("admin");
        message.setSenderName("Support Admin");
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
