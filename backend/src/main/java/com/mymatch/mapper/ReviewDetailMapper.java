package com.mymatch.mapper;

import com.mymatch.dto.request.reviewdetail.ReviewDetailRequest;
import com.mymatch.dto.response.review.ReviewResponse;
import com.mymatch.entity.ReviewCriteria;
import com.mymatch.entity.ReviewDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = {ReviewCriteriaMapper.class, ReviewResponse.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReviewDetailMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "review", ignore = true)
    @Mapping(target = "criteria", source = "criteria")
    @Mapping(target = "score", source = "request.score")
    @Mapping(target = "comment", source = "request.comment")
    @Mapping(target = "isYes", source = "request.isYes")
    ReviewDetail toReviewDetail(ReviewDetailRequest request, ReviewCriteria criteria);
}
