package com.mymatch.repository;

import com.mymatch.entity.WebSocketSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebSocketSessionRepository extends JpaRepository<WebSocketSession, Long> {
    void deleteBySocketSessionId(String socketSessionId);
    List<WebSocketSession> findAllByStudentIdIn(List<Long> studentIds);
}
