package com.mymatch.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.mymatch.dto.request.chatmessage.ChatMessageCreationRequest;
import com.mymatch.dto.response.ChatMessageResponse;

import java.util.List;

public interface ChatMessageService {
    ChatMessageResponse createChatMessage(ChatMessageCreationRequest request) throws JsonProcessingException;
    List<ChatMessageResponse> getMessages(Long conversationId);
}
