package com.mymatch.service.impl;

import com.mymatch.dto.request.reviewcriteria.ReviewCriteriaCreateRequest;
import com.mymatch.dto.request.reviewcriteria.ReviewCriteriaFilter;
import com.mymatch.dto.request.reviewcriteria.ReviewCriteriaUpdateRequest;
import com.mymatch.dto.response.reviewcriteria.ReviewCriteriaResponse;
import com.mymatch.entity.ReviewCriteria;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.ReviewCriteriaMapper;
import com.mymatch.repository.ReviewCriteriaRepository;
import com.mymatch.service.ReviewCriteriaService;
import com.mymatch.specification.ReviewCriteriaSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewCriteriaServiceImpl implements ReviewCriteriaService {

    ReviewCriteriaRepository reviewCriteriaRepository;
    ReviewCriteriaMapper reviewCriteriaMapper;

    @Override
    public List<ReviewCriteriaResponse> getAllReviewCriteria(ReviewCriteriaFilter filter) {
        return reviewCriteriaRepository.findAll(ReviewCriteriaSpecification.filter(filter))
                .stream()
                .map(reviewCriteriaMapper::toResponse)
                .toList();
    }

    @Override
    public ReviewCriteriaResponse getReviewCriteriaById(Long id) {
        ReviewCriteria reviewCriteria = reviewCriteriaRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_CRITERIA_NOT_FOUND));
        return reviewCriteriaMapper.toResponse(reviewCriteria);
    }

    @Override
    public ReviewCriteriaResponse createReviewCriteria(ReviewCriteriaCreateRequest request) {
        ReviewCriteria reviewCriteria = reviewCriteriaMapper.toEntity(request);
        ReviewCriteria saved = reviewCriteriaRepository.save(reviewCriteria);
        return reviewCriteriaMapper.toResponse(saved);
    }

    @Override
    public ReviewCriteriaResponse updateReviewCriteria(Long id, ReviewCriteriaUpdateRequest request) {
        ReviewCriteria reviewCriteria = reviewCriteriaRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_CRITERIA_NOT_FOUND));

        reviewCriteriaMapper.updateEntity(reviewCriteria, request);
        ReviewCriteria updated = reviewCriteriaRepository.save(reviewCriteria);

        return reviewCriteriaMapper.toResponse(updated);
    }

    @Override
    public void deleteReviewCriteria(Long id) {
        ReviewCriteria reviewCriteria = reviewCriteriaRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_CRITERIA_NOT_FOUND));
        reviewCriteriaRepository.delete(reviewCriteria);
    }
}
