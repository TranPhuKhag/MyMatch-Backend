package com.mymatch.mapper;

import com.mymatch.dto.response.teammember.TeamMemberResponse;
import com.mymatch.entity.TeamMember;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TeamMemberMapper {
    @Mapping(target = "member.note", ignore = true)
    TeamMemberResponse toResponse(TeamMember entity);
}
