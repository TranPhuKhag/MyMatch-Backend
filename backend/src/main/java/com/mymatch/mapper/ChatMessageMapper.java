package com.mymatch.mapper;

import com.mymatch.dto.request.chatmessage.ChatMessageCreationRequest;
import com.mymatch.dto.response.ChatMessageResponse;
import com.mymatch.entity.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ChatMessageMapper {
    ChatMessage toChatMessage(ChatMessageCreationRequest request);
    @Mapping(target = "sender.user.role", ignore = true) // Map full sender nếu cần, hoặc custom
    @Mapping(target = "sender.user.student", ignore = true)
    ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage);
}

