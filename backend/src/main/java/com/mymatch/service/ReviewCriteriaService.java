package com.mymatch.service;

import com.mymatch.dto.request.reviewcriteria.ReviewCriteriaCreateRequest;
import com.mymatch.dto.request.reviewcriteria.ReviewCriteriaFilter;
import com.mymatch.dto.request.reviewcriteria.ReviewCriteriaUpdateRequest;
import com.mymatch.dto.response.reviewcriteria.ReviewCriteriaResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface ReviewCriteriaService {

    List<ReviewCriteriaResponse> getAllReviewCriteria(ReviewCriteriaFilter filter);

    ReviewCriteriaResponse getReviewCriteriaById(Long id);

    @PreAuthorize("hasAuthority('reviewcriteria:create')")
    ReviewCriteriaResponse createReviewCriteria(ReviewCriteriaCreateRequest request);

    @PreAuthorize("hasAuthority('reviewcriteria:update')")
    ReviewCriteriaResponse updateReviewCriteria(Long id, ReviewCriteriaUpdateRequest request);

    @PreAuthorize("hasAuthority('reviewcriteria:delete')")
    void deleteReviewCriteria(Long id);

}
