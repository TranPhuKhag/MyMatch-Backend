package com.mymatch.service;

import com.mymatch.entity.WebSocketSession;
import com.mymatch.repository.WebSocketSessionRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebSocketSessionService {
    WebSocketSessionRepository webSocketSessionRepository;
    public WebSocketSession create(WebSocketSession session) {
        return webSocketSessionRepository.save(session);
    }
    @Transactional
    public void deleteBySessionId(String sessionId) {
        webSocketSessionRepository.deleteBySocketSessionId(sessionId);
    }
}
