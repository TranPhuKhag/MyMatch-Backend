package com.mymatch.service.impl;

import com.mymatch.dto.request.ChatMessageRequest;
import com.mymatch.dto.response.ChatMessageResponse;
import com.mymatch.entity.ChatMessage;
import com.mymatch.entity.Conversation;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.ChatMessageMapper;
import com.mymatch.repository.ChatMessageRepository;
import com.mymatch.repository.ConversationRepository;
import com.mymatch.service.ChatMessageService;
import com.mymatch.service.ConversationService;
import com.mymatch.utils.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatMessageServiceImpl implements ChatMessageService {
    ChatMessageMapper chatMessageMapper;
    ChatMessageRepository chatMessageRepository;
    ConversationService conversationService;
    ConversationRepository conversationRepository;

    @Override
    public ChatMessageResponse createChatMessage(ChatMessageRequest request) {
        ChatMessage chatMessage = chatMessageRepository.save(ChatMessage.builder()
                        .conversation(conversationRepository.findById(request.getConversationId())
                                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND)))
                        .message(request.getMessage())
                .build());
        return toChatMessageResponse(chatMessage);
    }
    private ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage) {
       var userId = SecurityUtil.getCurrentUserId();
       var chatMessageResponse = chatMessageMapper.toChatMessageResponse(chatMessage);
       chatMessageResponse.setMe(userId.equals(chatMessage.getSender().getUser().getId()));
       return chatMessageResponse;
    }
}
