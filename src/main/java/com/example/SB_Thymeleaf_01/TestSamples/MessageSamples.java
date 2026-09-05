package com.example.SB_Thymeleaf_01.TestSamples;

import com.example.SB_Thymeleaf_01.Models.ChatMessage;
import com.example.SB_Thymeleaf_01.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageSamples {

        @Autowired
        private ChatMessage sampleMessage1;
        @Autowired
        private ChatService  chatService;

        public void installArtificialMessagesOnDatabase() {
            sampleMessage1.setMessage("hi how are you");
            sampleMessage1.setSenderId("sender1");
            sampleMessage1.setReceiverId("reciever1");
            sampleMessage1.setSenderName("jhon");
            sampleMessage1.setSessionId("session01");

            System.out.println(" ");
            System.out.println(" ");
            System.out.println("sample messages are installed");

            chatService.sendMessage(sampleMessage1);
    }
}
