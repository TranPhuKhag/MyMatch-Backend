package com.mymatch.service;

import com.mymatch.dto.request.ChatMessageRequest;
import com.mymatch.dto.response.ChatMessageResponse;

public interface ChatMessageService {
    ChatMessageResponse createChatMessage(ChatMessageRequest request);
}
