package com.mymatch.controller;

import com.mymatch.dto.request.review.ReviewCreationRequest;
import com.mymatch.dto.request.review.ReviewFilterRequest;
import com.mymatch.dto.request.review.ReviewUpdateRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.review.ReviewResponse;
import com.mymatch.service.ReviewService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewController {

    ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ReviewResponse> createReview(@RequestBody ReviewCreationRequest req) {
        return ApiResponse.<ReviewResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Thêm review thành công")
                .result(reviewService.createReview(req))
                .build();
    }

    @PostMapping("/upload-evidence")
    public ApiResponse<String> uploadEvidenceFile(@RequestParam("file") MultipartFile file) {
        String fileUrl = reviewService.uploadEvidenceFile(file);
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Tải lên file thành công")
                .result(fileUrl)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ReviewResponse> getById(@PathVariable Long id) {
        return ApiResponse.<ReviewResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy review thành công")
                .result(reviewService.getById(id))
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Xoá review thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ReviewResponse> updateReview(
            @PathVariable Long id,
            @RequestBody @Valid ReviewUpdateRequest request
    ) {
        return ApiResponse.<ReviewResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật thông tin review thành công")
                .result(reviewService.updateReview(id, request))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<ReviewResponse>> getAllReviews(
            @ModelAttribute ReviewFilterRequest filter,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection
    ) {
        return ApiResponse.<PageResponse<ReviewResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách review thành công")
                .result(reviewService.getAllReviews(filter, page, size, sortBy, sortDirection))
                .build();
    }
}
