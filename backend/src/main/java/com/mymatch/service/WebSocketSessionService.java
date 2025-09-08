package com.mymatch.service;

import com.mymatch.entity.WebSocketSession;


public interface WebSocketSessionService {
    WebSocketSession create(WebSocketSession session);
    void deleteBySessionId(String sessionId);
}
