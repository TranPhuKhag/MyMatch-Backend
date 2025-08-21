package com.mymatch.dto.response.reviewdetail;

import com.mymatch.dto.response.reviewcriteria.ReviewCriteriaResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Response for each review detail
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewDetailResponse {
    Long id;
    ReviewCriteriaResponse criteria;
    int score;
    String comment;
    Boolean isYes;
}
