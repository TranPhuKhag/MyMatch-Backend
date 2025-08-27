package com.mymatch.mapper;

import com.mymatch.dto.request.ChatMessageRequest;
import com.mymatch.dto.response.ChatMessageResponse;
import com.mymatch.entity.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ChatMessageMapper {
    ChatMessage toChatMessage(ChatMessageRequest request);
    ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage);
}
