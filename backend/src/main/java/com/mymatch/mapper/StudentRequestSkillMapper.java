package com.mymatch.mapper;

import com.mymatch.dto.response.studentrequestskill.StudentRequestSkillResponse;
import com.mymatch.entity.StudentRequestSkill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentRequestSkillMapper {
    @Mapping(target = "requestId", source = "request.id")
    StudentRequestSkillResponse toResponse(StudentRequestSkill entity);
}
