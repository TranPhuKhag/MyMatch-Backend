package com.mymatch.repository;

import com.mymatch.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
