package com.mymatch.mapper;


import com.mymatch.dto.response.ConversationResponse;
import com.mymatch.entity.Conversation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {StudentMapper.class},
        nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface ConversationMapper {

    @Mapping(target = "participants", ignore = true)
    ConversationResponse toConversationResponse(Conversation conversation);
}
