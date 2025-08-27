package com.mymatch.repository;

import com.mymatch.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ConversationRepository  extends JpaRepository<Conversation, Long> {
}
