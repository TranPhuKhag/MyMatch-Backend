package com.mymatch.mapper;

import com.mymatch.dto.response.skill.SkillResponse;
import com.mymatch.entity.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SkillMapper {
    SkillResponse toResponse(Skill skill);
}
