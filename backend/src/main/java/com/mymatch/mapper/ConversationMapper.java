package com.mymatch.mapper;


import com.mymatch.dto.response.ConversationResponse;
import com.mymatch.entity.Conversation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface ConversationMapper {
    ConversationResponse toConversationResponse(Conversation conversation);
}
