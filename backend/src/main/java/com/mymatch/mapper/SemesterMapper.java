package com.mymatch.mapper;

import com.mymatch.dto.response.semester.SemesterResponse;
import com.mymatch.entity.Semester;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SemesterMapper {

    SemesterResponse toResponse(Semester semester);
}
