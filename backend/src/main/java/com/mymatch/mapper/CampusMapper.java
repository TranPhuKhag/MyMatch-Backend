package com.mymatch.mapper;

import com.mymatch.dto.request.campus.CampusCreationRequest;
import com.mymatch.dto.request.campus.CampusUpdateRequest;
import com.mymatch.dto.response.campus.CampusResponse;
import com.mymatch.entity.Campus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CampusMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "university", ignore = true)
    Campus toEntity(CampusCreationRequest req);

    CampusResponse toResponse(Campus entity);

    @Mapping(target = "university", ignore = true)
    void update(@MappingTarget Campus entity, CampusUpdateRequest req);
}
