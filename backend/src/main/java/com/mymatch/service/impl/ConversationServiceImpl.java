package com.mymatch.service.impl;

import com.mymatch.dto.request.conversation.ConversationCreationRequest;
import com.mymatch.dto.response.conversation.ConversationResponse;
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
import jakarta.transaction.Transactional;
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
        var currentUserId = SecurityUtil.getCurrentUserId();
        var me = studentRepository.findByUserId(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND));
        List<Conversation> conversations = conversationRepository.findAllByParticipants_Id(me.getId());
        return conversations.stream()
                .map(conversation -> {
                    var response = toConversationResponse(conversation);
                    Student other = conversation.getParticipants().stream()
                            .filter(s -> s.getUser() != null
                                    && !s.getUser().getId().equals(currentUserId))
                            .findFirst().orElse(null);
                    if (other != null && other.getUser() != null) {
                        response.setConversationAvatar(other.getUser().getAvatarUrl());
                        response.setConversationName(other.getUser().getUsername());
                    }
                    return response;
                })
                .toList();
    }

    @Override
    @Transactional
    public ConversationResponse createConversation(ConversationCreationRequest request) {
        // Fetch current user
        var currentUserId = SecurityUtil.getCurrentUserId();
        var me = studentRepository.findByUserId(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND));
        log.info("Current user ID: {}", me.getUser().getUsername());
        log.info("Current user: {}", me);
        log.info("Participant IDs: {}", request.getParticipantIds().getFirst());
        var otherStudent  = studentRepository.findById(request.getParticipantIds().getFirst())
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND));
        log.info("Other user: {}", otherStudent.getUser().getUsername() );
        if (me.getId().equals(otherStudent.getId())) {
            throw new AppException(ErrorCode.CANNOT_CREATE_CONVERSATION_WITH_YOURSELF);
        }

        // Generate hash (sorted)
        List<Long> participantIds = List.of(me.getId(), otherStudent.getId());
        String participantHash = generateParticipantHash(participantIds);

        // Check existing
        conversationRepository.findByParticipantsHash(participantHash)
                .ifPresent(c -> { throw new AppException(ErrorCode.CONVERSATION_ALREADY_EXISTS); });

        List<Student> participants = List.of(
                me,
                otherStudent
        );

        // Build Conversation info
        Conversation newConversation = Conversation.builder()
                .type(request.getType())
                .participants(participants)
                .participantsHash(participantHash)
                .avatarUrl(null)
                .title(null)
                .createdBy(me)
                .build();

        Conversation conversation = conversationRepository.save(newConversation);
        ConversationResponse conversationResponse = toConversationResponse(conversation);
        conversationResponse.setConversationAvatar(otherStudent.getUser().getAvatarUrl());
        conversationResponse.setConversationName(otherStudent.getUser().getUsername());
        return conversationResponse;
    }
    private String generateParticipantHash(List<Long> ids) {
        return ids.stream()
                .sorted()
                .map(String::valueOf)
                .collect(java.util.stream.Collectors.joining("-"));
    }

    private ConversationResponse toConversationResponse(Conversation conversation) {
        var userId = SecurityUtil.getCurrentUserId();
        var conversationResponse = conversationMapper.toConversationResponse(conversation);
        conversationResponse.setMe(conversation.getParticipants().stream()
                .anyMatch(participantInfo -> participantInfo.getUser().getId().equals(userId)));
        return conversationResponse;
    }
}
