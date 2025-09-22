package com.mymatch.mapper;

import com.mymatch.dto.request.teamrequest.TeamRequestCreationRequest;
import com.mymatch.dto.request.teamrequest.TeamRequestUpdateRequest;
import com.mymatch.dto.response.teamrequest.TeamRequestResponse;
import com.mymatch.entity.TeamRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TeamRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "urgency", source = "req.urgency", defaultValue = "NORMAL")
    @Mapping(target = "status", constant = "OPEN")
    TeamRequest toEntity(TeamRequestCreationRequest req);
    @Mapping(target = "team.createdBy", ignore = true)
    @Mapping(target = "skills", source = "skills")
    TeamRequestResponse toResponse(TeamRequest entity);
    void update(@MappingTarget TeamRequest entity, TeamRequestUpdateRequest req);
}
