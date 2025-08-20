package com.mymatch.controller;

import com.mymatch.dto.request.course.CourseCreationRequest;
import com.mymatch.dto.request.course.CourseFilterRequest;
import com.mymatch.dto.request.course.CourseUpdateRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.course.CourseResponse;
import com.mymatch.service.CourseService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseController {

    CourseService courseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CourseResponse> createCourse(@RequestBody @Valid CourseCreationRequest req) {
        return ApiResponse.<CourseResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Thêm môn học thành công")
                .result(courseService.createCourse(req))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CourseResponse> getById(@PathVariable Long id) {
        return ApiResponse.<CourseResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin môn học thành công")
                .result(courseService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<CourseResponse> updateCourse(@PathVariable Long id, @RequestBody @Valid CourseUpdateRequest req) {
        return ApiResponse.<CourseResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật môn học thành công")
                .result(courseService.updateCourse(id, req))
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Xoá môn học thành công")
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<CourseResponse>> getAllCourses(
            @ModelAttribute CourseFilterRequest filter,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "code") String sort) {
        return ApiResponse.<PageResponse<CourseResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách môn học thành công")
                .result(courseService.getAllCourses(filter, page, size, sort))
                .build();
    }
}
