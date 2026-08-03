package com.example.SB_Thymeleaf_01.Repositories;

import com.example.SB_Thymeleaf_01.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findBySenderIdAndReceiverIdOrderByTimestampAsc(String senderId, String receiverId);

   List<ChatMessage> findBySessionIdOrderByTimestampAsc(String sessionId);

    @Query("SELECT m FROM ChatMessage m WHERE m.receiverId = :receiverId AND m.status = 'UNREAD'")
    List<ChatMessage> findUnreadMessages (@Param("receiverId") String receiverId);
}
