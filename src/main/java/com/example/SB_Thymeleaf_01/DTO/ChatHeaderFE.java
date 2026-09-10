package com.example.SB_Thymeleaf_01.DTO;

import com.example.SB_Thymeleaf_01.Models.ChatMessage;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ChatHeaderFE {

    private Long id;
    private String receiverId;
    private String senderId;
    private String senderName;
    @Enumerated(EnumType.STRING)
    private ChatMessage.MessageType type;

    @Enumerated(EnumType.STRING)
    private ChatMessage.MessageStatus status;

    public enum MessageStatus {
        SENT, DELIVERED, READ, UNREAD, FAILED
    }

    public enum MessageType {
        CHAT, JOIN, LEAVE, TYPING
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public ChatMessage.MessageType getType() {
        return type;
    }

    public void setType(ChatMessage.MessageType type) {
        this.type = type;
    }

    public ChatMessage.MessageStatus getStatus() {
        return status;
    }

    public void setStatus(ChatMessage.MessageStatus status) {
        this.status = status;
    }
}
