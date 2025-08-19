package com.mymatch.mapper;

import com.mymatch.dto.request.university.UniversityCreationRequest;
import com.mymatch.dto.request.university.UniversityUpdateRequest;
import com.mymatch.dto.response.university.UniversityResponse;
import com.mymatch.entity.University;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UniversityMapper {
    @Mapping(target = "id", ignore = true)
    University toEntity(UniversityCreationRequest req);

    UniversityResponse toResponse(University entity);

    void update(@MappingTarget University entity, UniversityUpdateRequest req);
}
