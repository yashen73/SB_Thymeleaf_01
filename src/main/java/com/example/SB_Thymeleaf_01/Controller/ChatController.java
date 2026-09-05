package com.example.SB_Thymeleaf_01.Controller;

import com.example.SB_Thymeleaf_01.Models.ChatMessage;
import com.example.SB_Thymeleaf_01.Models.Customer;
import com.example.SB_Thymeleaf_01.Service.ChatService;
import com.example.SB_Thymeleaf_01.Service.CustomerSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.*;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private CustomerSerivce customerSerivce;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @MessageMapping("/chat.SendMessage")
    public ChatMessage SendMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
           //Make a mapping to the Header Attributes
            Map<String, Object> sessionAttributesToSendMsg = headerAccessor.getSessionAttributes();
            //take SenderID which is allocated from AddUser
            String senderIdToSendMsg = sessionAttributesToSendMsg.get("senderId").toString();
            //Check the nullity and settting SenderID using session Attributes
            if(!senderIdToSendMsg.isEmpty()){
                chatMessage.setSenderId(senderIdToSendMsg);
            }

            String sessionId = (String) headerAccessor.getSessionAttributes().get("sessionId");

            if(sessionId == null) {
                sessionId = UUID.randomUUID().toString();
                headerAccessor.getSessionAttributes().put("sessionId", sessionId);
            }else {
                chatMessage.setSessionId(sessionId);
            }
            System.out.println("");
            System.out.println("");
            System.out.println("/chat.SendMessage is called from JS and session ID is .... "+ sessionId);
            return chatService.sendMessage(chatMessage);
    }



    @MessageMapping("/chat.addUser")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,Principal principal, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("Principle is " + principal.getName());

        String usermailForAddUser = principal.getName();
        String sessionId = usermailForAddUser + "_" + UUID.randomUUID().toString();

        //find & Set Customer Details according to the Mail
        Optional<Customer> custForAddUser = customerSerivce.findAnyCustomer(usermailForAddUser);
        if (custForAddUser.isPresent()) {
            System.out.println("customer details are : "+ custForAddUser.get());
            headerAccessor.getSessionAttributes().put("senderId", custForAddUser.get().getId());
            chatMessage.setSenderId(custForAddUser.get().getId().toString());
        }

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


    @MessageMapping("/chat.loadOldChat")
    public List<ChatMessage> loadOldChat(ChatMessage chatMessage, Principal principal, SimpMessageHeaderAccessor headerAccessor) {

        //Grabbing sender Mail from principal
        String senderMail = principal.getName();
        //Grabbing senderId using Session Attributes...
        String senderId = headerAccessor.getSessionAttributes().get("senderId").toString();
        System.out.println("Sender Id is : "+senderId);

        List<ChatMessage> oldMessages = chatService.getConversation(senderId, "admin");
        System.out.println("Old messages of are"+oldMessages);

        simpMessagingTemplate.convertAndSendToUser(
                senderMail,
                "/queue/oldMessages",
                oldMessages
        );

        return oldMessages;
    }
}
