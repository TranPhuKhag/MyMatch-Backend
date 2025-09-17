package com.mymatch.mapper;

import com.mymatch.dto.response.lecturercourse.LecturerCourseResponse;
import com.mymatch.entity.LecturerCourse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LecturerCourseMapper {
    LecturerCourseResponse toResponse(LecturerCourse lecturerCourse);
    List<LecturerCourseResponse> toResponseList(List<LecturerCourse> lecturerCourses);
}
