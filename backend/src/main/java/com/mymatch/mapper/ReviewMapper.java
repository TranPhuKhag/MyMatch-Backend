package com.mymatch.mapper;

import com.mymatch.dto.response.review.ReviewResponse;
import com.mymatch.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReviewMapper {
    ReviewResponse toReviewResponse(Review review);
}
