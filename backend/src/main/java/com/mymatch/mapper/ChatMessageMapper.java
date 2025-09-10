package com.mymatch.mapper;

import com.mymatch.dto.request.chatmessage.ChatMessageCreationRequest;
import com.mymatch.dto.response.chatmessage.ChatMessageResponse;
import com.mymatch.entity.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = {ConversationMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ChatMessageMapper {
    ChatMessage toChatMessage(ChatMessageCreationRequest request);
    @Mapping(target = "sender.user.role", ignore = true)
    @Mapping(target = "sender.user.student", ignore = true)
    @Mapping(target = "sender.campus", ignore = true)
    ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage);
}

