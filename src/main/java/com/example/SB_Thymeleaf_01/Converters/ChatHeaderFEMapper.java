package com.example.SB_Thymeleaf_01.Converters;

import com.example.SB_Thymeleaf_01.DTO.ChatHeaderFE;
import com.example.SB_Thymeleaf_01.Models.ChatMessage;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

@UtilityClass
public class ChatHeaderFEMapper {

    public static ChatMessage toEntity(ChatHeaderFE dto) {
        ChatMessage chatMessage = new ChatMessage();

        chatMessage.setReceiverId(dto.getReceiverId());
        chatMessage.setSenderId(dto.getSenderId());
        chatMessage.setSenderName(dto.getSenderName());
        chatMessage.setStatus(dto.getStatus());
        chatMessage.setType(dto.getType());

        return chatMessage;
    }

    public static ChatHeaderFE toDto(ChatMessage chatMessage){
        ChatHeaderFE chatHeaderFE = new ChatHeaderFE();

        chatHeaderFE.setId(chatMessage.getId());
        chatHeaderFE.setReceiverId(chatMessage.getReceiverId());
        chatHeaderFE.setSenderId(chatMessage.getSenderId());
        chatHeaderFE.setSenderName(chatMessage.getSenderName());
        chatHeaderFE.setType(chatMessage.getType());
        chatHeaderFE.setStatus(chatMessage.getStatus());

        return chatHeaderFE;
    }
}
