package com.mymatch.mapper;

import com.mymatch.dto.request.swap.SwapCreationRequest;
import com.mymatch.dto.response.swap.SwapResponse;
import com.mymatch.entity.Swap;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SwapMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "requestFrom", ignore = true)
    @Mapping(target = "requestTo", ignore = true)
    @Mapping(target = "studentFrom", ignore = true)
    @Mapping(target = "studentFrom.user.role", ignore = true)
    @Mapping(target = "studentFrom.user.student", ignore = true)
    @Mapping(target = "studentTo", ignore = true)
    @Mapping(target = "fromDecision", ignore = true)
    @Mapping(target = "toDecision", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "matchedAt", ignore = true)
    Swap toEntity(SwapCreationRequest req);
    @Mapping(target = "requestFrom", source = "requestFrom")
    @Mapping(target = "requestFrom.student.user.role", ignore = true)
    @Mapping(target = "requestTo", source = "requestTo")
    @Mapping(target = "requestTo.student.user.role", ignore = true)
    @Mapping(target = "studentFrom", source = "studentFrom")
    @Mapping(target = "studentFrom.user.role", ignore = true)
    @Mapping(target = "studentFrom.user.student", ignore = true)
    @Mapping(target = "studentTo", source = "studentTo")
    @Mapping(target = "studentTo.user.role", ignore = true)
    @Mapping(target = "studentTo.user.student", ignore = true)
    SwapResponse toResponse(Swap swap);
}
