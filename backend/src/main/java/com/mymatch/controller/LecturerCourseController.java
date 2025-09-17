package com.mymatch.controller;

import com.mymatch.dto.request.lecturercourse.LecturerCourseCreationRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.lecturercourse.LecturerCourseResponse;
import com.mymatch.service.LecturerCourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lecturer-courses")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class LecturerCourseController {

    LecturerCourseService lecturerCourseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<LecturerCourseResponse> assign(
            @Valid @RequestBody LecturerCourseCreationRequest req
    ) {
        return ApiResponse.<LecturerCourseResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Gán môn học cho giảng viên thành công")
                .result(lecturerCourseService.assign(req))
                .build();
    }

    @GetMapping("/lecturers/{lecturerId}")
    public ApiResponse<List<LecturerCourseResponse>> getByLecturerId(
            @PathVariable Long lecturerId
    ) {
        return ApiResponse.<List<LecturerCourseResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách môn giảng dạy theo giảng viên thành công")
                .result(lecturerCourseService.getByLecturerId(lecturerId))
                .build();
    }
}
