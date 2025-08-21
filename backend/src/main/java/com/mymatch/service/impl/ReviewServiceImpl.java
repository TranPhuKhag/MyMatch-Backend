package com.mymatch.service.impl;

import com.mymatch.dto.request.review.ReviewCreationRequest;
import com.mymatch.dto.request.review.ReviewFilterRequest;
import com.mymatch.dto.request.review.ReviewUpdateRequest;
import com.mymatch.dto.request.reviewdetail.ReviewDetailRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.review.ReviewResponse;
import com.mymatch.entity.*;
import com.mymatch.enums.CriteriaType;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.ReviewDetailMapper;
import com.mymatch.mapper.ReviewMapper;
import com.mymatch.repository.*;
import com.mymatch.service.ReviewService;
import com.mymatch.specification.ReviewSpecification;
import com.mymatch.utils.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewServiceImpl implements ReviewService {
    LecturerRepository lecturerRepository;
    StudentRepository studentRepository;
    ReviewDetailRepository reviewDetailRepository;
    SemesterRepository semesterRepository;
    ReviewRepository reviewRepository;
    CourseRepository courseRepository;
    ReviewDetailMapper reviewDetailMapper;
    ReviewMapper reviewMapper;
    ReviewCriteriaRepository reviewCriteriaRepository;


    @Override
    @Transactional
    public ReviewResponse createReview(ReviewCreationRequest request) {
        Lecturer lecturer = lecturerRepository.findById(request.getLecturerId())
                .orElseThrow(() -> new AppException(ErrorCode.LECTURER_NOT_FOUND));
        Student student = studentRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND));
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));

        Review review = reviewMapper.toReview(request, lecturer, course, student);
        if (request.getSemesterId() != null) {
            Semester semester = semesterRepository.findById(request.getSemesterId())
                    .orElseThrow(() -> new AppException(ErrorCode.SEMESTER_NOT_FOUND));
            review.setSemester(semester);
        }
        review = reviewRepository.save(review);
        List<ReviewDetail> details = new ArrayList<>();
        for (ReviewDetailRequest detail : request.getDetails()) {
            ReviewCriteria criteria = reviewCriteriaRepository.findById(detail.getCriteriaId())
                    .orElseThrow(() -> new AppException(ErrorCode.REVIEW_CRITERIA_NOT_FOUND));
            ReviewDetail reviewDetail = reviewDetailMapper.toReviewDetail(detail, criteria);
            reviewDetail.setReview(review);
            details.add(reviewDetail);
        }
        details = reviewDetailRepository.saveAll(details);
        review.setDetails(details);
        review.setOverallScore(setOverallScore(details));
        review = reviewRepository.save(review);
        return reviewMapper.toReviewResponse(review);
    }

    @Override
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        Long currentUserId = SecurityUtil.getCurrentUserId();
        boolean isAdmin = SecurityUtil.hasAuthority("review:delete"); // check if admin

        if (!isAdmin && !review.getStudent().getUser().getId().equals(currentUserId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        reviewRepository.delete(review);
    }

    @Override
    public PageResponse<ReviewResponse> getAllReviews(ReviewFilterRequest filterRequest,
                                                      int page,
                                                      int size,
                                                      String sortBy,
                                                      String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromOptionalString(sortDirection)
                .orElse(Sort.Direction.DESC);

        Sort sort = Sort.by(direction, sortBy != null ? sortBy : "createdAt");

        Pageable pageable = PageRequest.of(
                Math.max(page - 1, 0),
                Math.max(size, 1),
                sort
        );


        Page<Review> pages = reviewRepository.findAll(
                ReviewSpecification.byFilter(filterRequest),
                pageable
        );
        List<ReviewResponse> reviewResponses = pages.getContent().stream()
                .map(reviewMapper::toReviewResponse)
                .toList();

        return PageResponse.<ReviewResponse>builder()
                .data(reviewResponses)
                .pageSize(pages.getSize())
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .currentPage(page)
                .build();
    }


    @Override
    public ReviewResponse getById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));
        return reviewMapper.toReviewResponse(review);
    }

    @Override
    public ReviewResponse updateReview(Long reviewId, ReviewUpdateRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        Long currentUserId = SecurityUtil.getCurrentUserId();
        boolean isAdmin = SecurityUtil.hasAuthority("review:update"); // check if admin

        if (!isAdmin && !review.getStudent().getUser().getId().equals(currentUserId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        reviewMapper.updateReview(review, request);
        reviewRepository.save(review);
        return reviewMapper.toReviewResponse(review);
    }

    private Double setOverallScore(List<ReviewDetail> details) {
        if (details == null || details.isEmpty()) {
            return 0.0;
        }

        double totalScore = 0;
        int numberOfCriteria = 0;

        for (ReviewDetail detail : details) {
            if (detail.getCriteria() != null
                    && detail.getCriteria().getType().equals(CriteriaType.mark)) {
                totalScore += detail.getScore();
                numberOfCriteria++;
            }
        }
        if (numberOfCriteria == 0) return 0.0;
        double avg = totalScore / numberOfCriteria;
        // làm tròn 1 chữ số thập phân (vd: 8.333 -> 8.3)
        return Math.round(avg * 10.0) / 10.0;
    }
}
