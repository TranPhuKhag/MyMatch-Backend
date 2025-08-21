package com.mymatch.mapper;

import com.mymatch.dto.request.student.StudentCreationRequest;
import com.mymatch.dto.request.student.StudentUpdateRequest;
import com.mymatch.dto.response.student.StudentResponse;
import com.mymatch.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = {UserMapper.class, CampusMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "campus", ignore = true)
    @Mapping(target = "user", ignore = true)
    Student toEntity(StudentCreationRequest req);

    StudentResponse toResponse(Student entity);

    @Mapping(target = "campus", ignore = true)
    @Mapping(target = "user", ignore = true)
    void update(@MappingTarget Student entity, StudentUpdateRequest req);
}
