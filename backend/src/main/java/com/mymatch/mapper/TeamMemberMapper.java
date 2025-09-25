package com.mymatch.mapper;

import com.mymatch.dto.response.teammember.TeamMemberResponse;
import com.mymatch.entity.TeamMember;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TeamMemberMapper {
    TeamMemberResponse toResponse(TeamMember entity);
}
