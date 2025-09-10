package com.mymatch.service;

import com.mymatch.dto.request.conversation.ConversationCreationRequest;
import com.mymatch.dto.response.conversation.ConversationResponse;

import java.util.List;

public interface ConversationService {
    List<ConversationResponse> myConversations();
    ConversationResponse createConversation(ConversationCreationRequest request);
}
