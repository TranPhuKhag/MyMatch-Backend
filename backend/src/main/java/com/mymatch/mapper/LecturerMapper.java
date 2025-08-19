package com.mymatch.mapper;

import com.mymatch.dto.request.lecturer.LecturerCreationRequest;
import com.mymatch.dto.request.lecturer.LecturerUpdateRequest;
import com.mymatch.dto.response.lecturer.LecturerResponse;
import com.mymatch.entity.Campus;
import com.mymatch.entity.Lecturer;
import com.mymatch.entity.Review;
import com.mymatch.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {CampusMapper.class, TagMapper.class, ReviewMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LecturerMapper {

    @Mapping(target = "campus", source = "campus")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "tags", ignore = true)
    Lecturer toLecturer(LecturerCreationRequest request, Campus campus);

    @Mapping(target = "campus", source = "lecturer.campus")
    @Mapping(target = "reviewCount", source = "reviewCount")
    @Mapping(target = "id", source = "lecturer.id")
    @Mapping(target = "name", source = "lecturer.name")
    @Mapping(target = "code", source = "lecturer.code")
    @Mapping(target = "bio", source = "lecturer.bio")
    @Mapping(target = "tags", source = "lecturer.tags")
    @Mapping(target = "reviews", source = "reviews")
    LecturerResponse toLecturerResponse(Lecturer lecturer, List<Review> reviews, int reviewCount);

    @Mapping(target = "campus", source = "campus")
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "code", source = "request.code")
    @Mapping(target = "bio", source = "request.bio")
    void update(@MappingTarget Lecturer lecturer, LecturerUpdateRequest request,
                Campus campus, List<Tag> tags);
}
