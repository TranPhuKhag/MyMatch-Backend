package com.mymatch.service.impl;

import com.mymatch.entity.WebSocketSession;
import com.mymatch.repository.WebSocketSessionRepository;
import com.mymatch.service.WebSocketSessionService;
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
public class WebSocketSessionServiceImpl implements WebSocketSessionService {
    WebSocketSessionRepository webSocketSessionRepository;
    @Override
    public WebSocketSession create(WebSocketSession session) {
        return webSocketSessionRepository.save(session);
    }
    @Override
    @Transactional
    public void deleteBySessionId(String sessionId) {
        webSocketSessionRepository.deleteBySocketSessionId(sessionId);
    }
}
