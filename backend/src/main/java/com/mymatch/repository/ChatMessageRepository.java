package com.mymatch.repository;

import com.mymatch.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByConversationIdOrderByCreateAtAsc(Long conversationId);
}
