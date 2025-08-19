package com.mymatch.controller;

import com.mymatch.dto.request.university.UniversityCreationRequest;
import com.mymatch.dto.request.university.UniversityUpdateRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.university.UniversityResponse;
import com.mymatch.service.UniversityService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/universities")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UniversityController {

    UniversityService universityService;

    @PostMapping
    public ApiResponse<UniversityResponse> createUniversity(@RequestBody UniversityCreationRequest req) {
        return ApiResponse.<UniversityResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tạo trường đại học thành công")
                .result(universityService.createUniversity(req))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UniversityResponse> getById(@PathVariable Long id) {
        return ApiResponse.<UniversityResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin trường đại học thành công")
                .result(universityService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UniversityResponse> updateUniversity(@PathVariable Long id, @RequestBody UniversityUpdateRequest req) {
        return ApiResponse.<UniversityResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật trường đại học thành công")
                .result(universityService.updateUniversity(id, req))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<UniversityResponse> deleteUniversity(@PathVariable Long id) {
        return ApiResponse.<UniversityResponse>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Xoá trường đại học thành công")
                .result(universityService.deleteUniversity(id))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<UniversityResponse>> getAllUniversities(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {
        return ApiResponse.<PageResponse<UniversityResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách trường đại học thành công")
                .result(universityService.getAllUniversities(page, size, sort))
                .build();
    }
}