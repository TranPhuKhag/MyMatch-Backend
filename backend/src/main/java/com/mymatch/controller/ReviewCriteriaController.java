package com.mymatch.controller;

import com.mymatch.dto.request.reviewcriteria.ReviewCriteriaCreateRequest;
import com.mymatch.dto.request.reviewcriteria.ReviewCriteriaFilter;
import com.mymatch.dto.request.reviewcriteria.ReviewCriteriaUpdateRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.reviewcriteria.ReviewCriteriaResponse;
import com.mymatch.service.ReviewCriteriaService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review-criteria")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewCriteriaController {

    ReviewCriteriaService reviewCriteriaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ReviewCriteriaResponse> createReviewCriteria(@RequestBody ReviewCriteriaCreateRequest req) {
        return ApiResponse.<ReviewCriteriaResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tạo tiêu chí đánh giá thành công")
                .result(reviewCriteriaService.createReviewCriteria(req))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ReviewCriteriaResponse> getById(@PathVariable Long id) {
        return ApiResponse.<ReviewCriteriaResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin tiêu chí đánh giá thành công")
                .result(reviewCriteriaService.getReviewCriteriaById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ReviewCriteriaResponse> updateReviewCriteria(@PathVariable Long id,
                                                                    @RequestBody ReviewCriteriaUpdateRequest req) {
        return ApiResponse.<ReviewCriteriaResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật tiêu chí đánh giá thành công")
                .result(reviewCriteriaService.updateReviewCriteria(id, req))
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteReviewCriteria(@PathVariable Long id) {
        reviewCriteriaService.deleteReviewCriteria(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Xoá tiêu chí đánh giá thành công")
                .build();
    }

    @GetMapping
    public ApiResponse<List<ReviewCriteriaResponse>> getAllReviewCriteria(@ModelAttribute ReviewCriteriaFilter filter) {
        return ApiResponse.<List<ReviewCriteriaResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách tiêu chí đánh giá thành công")
                .result(reviewCriteriaService.getAllReviewCriteria(filter))
                .build();
    }
}
