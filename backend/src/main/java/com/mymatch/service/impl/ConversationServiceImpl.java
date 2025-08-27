package com.mymatch.service.impl;

import com.mymatch.dto.request.ConversationRequest;
import com.mymatch.dto.response.ConversationResponse;
import com.mymatch.entity.Conversation;
import com.mymatch.entity.Student;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.ConversationMapper;
import com.mymatch.repository.ConversationRepository;
import com.mymatch.repository.StudentRepository;
import com.mymatch.repository.UserRepository;
import com.mymatch.service.ConversationService;
import com.mymatch.utils.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConversationServiceImpl implements ConversationService {
    ConversationRepository conversationRepository;
    StudentRepository studentRepository;
    UserRepository userRepository;
    ConversationMapper conversationMapper;
    @Override
    public List<ConversationResponse> myConversations() {
    return null;
    }

    @Override
    public ConversationResponse createConversation(ConversationRequest request) {
        return null;
    }
    private ConversationResponse toConversationResponse(Conversation conversation) {
        var userId = SecurityUtil.getCurrentUserId();
        var conversationResponse = conversationMapper.toConversationResponse(conversation);
        conversationResponse.setMe(conversation.getParticipants().stream()
                .anyMatch(participantInfo -> participantInfo.getUser().getId().equals(userId)));
        return conversationResponse;
    }
    }
