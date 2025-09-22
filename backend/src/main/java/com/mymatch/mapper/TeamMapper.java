package com.mymatch.mapper;

import com.mymatch.dto.request.team.TeamCreationRequest;
import com.mymatch.dto.request.team.TeamUpdateRequest;
import com.mymatch.dto.response.team.TeamResponse;
import com.mymatch.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {SemesterMapper.class, CampusMapper.class, CourseMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TeamMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "semester", source = "semester")
    @Mapping(target = "campus", source = "campus")
    @Mapping(target = "course", source = "course")
    @Mapping(target = "name", source = "req.name")
    Team toEntity(TeamCreationRequest req, Semester semester, Campus campus, Course course);

    @Mapping(target = "createdBy.user", ignore = true)
    TeamResponse toResponse(Team team);

    void update(@MappingTarget Team team, TeamUpdateRequest req);
}
