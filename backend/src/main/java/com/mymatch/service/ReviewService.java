package com.mymatch.service;

import com.mymatch.dto.request.review.ReviewCreationRequest;
import com.mymatch.dto.request.review.ReviewFilterRequest;
import com.mymatch.dto.request.review.ReviewUpdateRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.review.ReviewResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewService {

    @PreAuthorize("hasAuthority('review:create')")
    ReviewResponse createReview(ReviewCreationRequest request);

    //Authorize check when implement
    void deleteReview(Long reviewId);

    @PreAuthorize("hasAuthority('review:read')")
    PageResponse<ReviewResponse> getAllReviews(ReviewFilterRequest filterRequest, int page, int size, String sortBy, String sortDirection);

    @PreAuthorize("hasAuthority('review:read')")
    ReviewResponse getById(Long id);

    //Authorize check when implement
    ReviewResponse updateReview(Long reviewId, ReviewUpdateRequest request);

    String uploadEvidenceFile(MultipartFile file);
}
