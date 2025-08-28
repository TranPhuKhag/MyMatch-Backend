package com.mymatch.repository;

import com.mymatch.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository  extends JpaRepository<Conversation, Long> {
    Optional<Conversation> findByParticipantsHash(String hash);

    List<Conversation> findAllByParticipants_Id(Long senderId);

    @Query("SELECT c FROM Conversation c JOIN c.participants p WHERE p.id = :studentId")
    List<Conversation> findAllByParticipantId(@Param("studentId") Long studentId);
//    @Query("{'participants_hash.senderId' : ?0}")
//    List<Conversation> findAllByParticipantIdsContains(Long senderId);

}
