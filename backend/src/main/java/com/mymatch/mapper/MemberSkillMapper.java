package com.mymatch.mapper;

import com.mymatch.dto.response.member.MemberSkillResponse;
import com.mymatch.entity.MemberSkill;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MemberSkillMapper {
    MemberSkillResponse toResponse(MemberSkill memberSkill);
}
