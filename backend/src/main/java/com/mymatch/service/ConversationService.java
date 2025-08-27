package com.mymatch.service;

import com.mymatch.dto.request.ConversationRequest;
import com.mymatch.dto.response.ConversationResponse;

import java.util.List;

public interface ConversationService {
    List<ConversationResponse> myConversations();
    ConversationResponse createConversation(ConversationRequest request);
}
