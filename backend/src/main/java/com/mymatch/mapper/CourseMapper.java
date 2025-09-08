package com.mymatch.mapper;

import com.mymatch.dto.request.course.CourseCreationRequest;
import com.mymatch.dto.request.course.CourseUpdateRequest;
import com.mymatch.dto.response.course.CourseResponse;
import com.mymatch.entity.Course;
import com.mymatch.entity.University;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CourseMapper {

    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "code", source = "request.code")
    @Mapping(target = "university", source = "university")
    Course toCourse(CourseCreationRequest request, University university);

    CourseResponse toCourseResponse(Course course);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "university", ignore = true)
    void update(@MappingTarget Course course, CourseUpdateRequest request);
}
