package com.mymatch.service.impl;

import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mymatch.dto.request.chatmessage.ChatMessageCreationRequest;
import com.mymatch.dto.response.chatmessage.ChatMessageResponse;
import com.mymatch.entity.ChatMessage;
import com.mymatch.entity.Conversation;
import com.mymatch.entity.Student;
import com.mymatch.entity.WebSocketSession;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.ChatMessageMapper;
import com.mymatch.repository.ChatMessageRepository;
import com.mymatch.repository.ConversationRepository;
import com.mymatch.repository.StudentRepository;
import com.mymatch.repository.WebSocketSessionRepository;
import com.mymatch.service.ChatMessageService;
import com.mymatch.service.ConversationService;
import com.mymatch.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatMessageServiceImpl implements ChatMessageService {
    ChatMessageMapper chatMessageMapper;
    ChatMessageRepository chatMessageRepository;
    ConversationService conversationService;
    ConversationRepository conversationRepository;
    SocketIOServer socketIOServer;
    WebSocketSessionRepository webSocketSessionRepository;
    ObjectMapper objectMapper;
    StudentRepository studentRepository;
    @Override
    @Transactional
    public ChatMessageResponse createChatMessage(ChatMessageCreationRequest request) throws JsonProcessingException {
        // Get current student (sender)
        Long currentUserId = SecurityUtil.getCurrentUserId();
        Student sender = studentRepository.findByUserId(currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND));

        // Validate conversationId
        Conversation conversation = conversationRepository.findById(request.getConversationId())
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));
        // Check if sender is a participant of the conversation
        boolean isParticipant = conversation.getParticipants().stream()
                .anyMatch(p -> p.getId().equals(sender.getId()));
        if (!isParticipant) {
            throw new AppException(ErrorCode.UNAUTHORIZED_ACCESS_CONVERSATION);
        }

        // Create chat message
        ChatMessage chatMessage = ChatMessage.builder()
                        .sender(sender)
                        .conversation(conversation)
                        .message(request.getMessage())
                .build();

        // Get studentIds info of participants in the conversation
        List<Long> studentIds =  conversation.getParticipants().stream()
                .map(Student::getId).toList();
        Map<String, WebSocketSession> webSocketSessions = webSocketSessionRepository
                .findAllByStudentIdIn(studentIds).stream()
                .collect(Collectors.toMap(
                        WebSocketSession::getSocketSessionId,
                        Function.identity()
                ));
        ChatMessageResponse chatMessageResponse = toChatMessageResponse(chatMessageRepository.save(chatMessage));

        //Publish event websocket to client
        socketIOServer.getAllClients().forEach(client -> {
            var webSocketSession = webSocketSessions.get(client.getSessionId().toString());
            if (Objects.nonNull(webSocketSession)) {
                String message = null;
                try {
                    chatMessageResponse.setMe(webSocketSession.getStudentId().equals(sender.getId()));
                    message = objectMapper.writeValueAsString(chatMessageResponse);
                    client.sendEvent("message", message);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        // Convert to response
        return toChatMessageResponse(chatMessage);
    }
    private ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage) {
       var userId = SecurityUtil.getCurrentUserId();
       var chatMessageResponse = chatMessageMapper.toChatMessageResponse(chatMessage);
       chatMessageResponse.setMe(userId.equals(chatMessage.getSender().getUser().getId()));
       return chatMessageResponse;
    }

    @Override
    public List<ChatMessageResponse> getMessages(Long conversationId) {
        // Validate conversationId
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));
        // Check if current user is a participant of the conversation
        Long currentUserId = SecurityUtil.getCurrentUserId();
        boolean isParticipant = conversation.getParticipants().stream()
                .anyMatch(p -> p.getUser().getId().equals(currentUserId));
        if (!isParticipant) {
            throw new AppException(ErrorCode.UNAUTHORIZED_ACCESS_CONVERSATION);
        }
        List<ChatMessage> chatMessages = chatMessageRepository.findAllByConversationIdOrderByCreateAtAsc(conversationId);
        return chatMessages.stream()
                .map(this::toChatMessageResponse)
                .toList();
    }
}
