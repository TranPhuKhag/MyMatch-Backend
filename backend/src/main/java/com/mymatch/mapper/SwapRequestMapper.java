package com.mymatch.mapper;

import com.mymatch.dto.request.swaprequest.SwapRequestCreationRequest;
import com.mymatch.dto.request.swaprequest.SwapRequestUpdateRequest;
import com.mymatch.dto.response.swaprequest.SwapRequestResponse;
import com.mymatch.entity.SwapRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SwapRequestMapper {
    @Mapping(target = "student.id", source = "student.id")
    @Mapping(target = "course.id", source = "course.id")
    @Mapping(target = "student.user", ignore = true)
    @Mapping(target = "lecturerFrom.id",source = "lecturerFrom.id")
    @Mapping(target = "lecturerTo.id",source = "lecturerTo.id")

    SwapRequestResponse toResponse(SwapRequest entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "course", ignore = true)
    SwapRequest toEntity(SwapRequestCreationRequest request);

    void update(@MappingTarget SwapRequest entity, SwapRequestUpdateRequest request);
}
