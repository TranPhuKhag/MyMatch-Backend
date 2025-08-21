package com.mymatch.mapper;

import com.mymatch.dto.request.review.ReviewCreationRequest;
import com.mymatch.dto.request.review.ReviewUpdateRequest;
import com.mymatch.dto.response.review.ReviewResponse;
import com.mymatch.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {SemesterMapper.class, StudentMapper.class, CourseMapper.class, ReviewDetailMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReviewMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", source = "student")
    @Mapping(target = "course", source = "course")
    @Mapping(target = "lecturer", source = "lecturer")
    @Mapping(target = "overallScore", ignore = true)
    @Mapping(target = "details", ignore = true)
    @Mapping(target = "isVerified", ignore = true)
    @Mapping(target = "semester", ignore = true)
    @Mapping(target = "isAnonymous", source = "request.isAnonymous")
    @Mapping(target = "evidenceUrl", source = "request.evidenceUrl")
    Review toReview(ReviewCreationRequest request, Lecturer lecturer, Course course, Student student);


    ReviewResponse toReviewResponse(Review review);

    void updateReview(@MappingTarget Review review, ReviewUpdateRequest request);
}
