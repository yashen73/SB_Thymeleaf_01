package com.example.SB_Thymeleaf_01.TestSamples;

import com.example.SB_Thymeleaf_01.ChatMessage;
import com.example.SB_Thymeleaf_01.Service.ChatService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

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
