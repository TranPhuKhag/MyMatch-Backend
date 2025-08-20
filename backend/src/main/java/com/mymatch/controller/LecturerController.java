package com.mymatch.controller;

import com.mymatch.dto.request.lecturer.LecturerCreationRequest;
import com.mymatch.dto.request.lecturer.LecturerFilterRequest;
import com.mymatch.dto.request.lecturer.LecturerUpdateRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.lecturer.LecturerResponse;
import com.mymatch.service.LecturerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lecturers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LecturerController {

    LecturerService lecturerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<LecturerResponse> createLecturer(@RequestBody LecturerCreationRequest req) {
        return ApiResponse.<LecturerResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tạo giảng viên thành công")
                .result(lecturerService.createLecturer(req))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<LecturerResponse> getById(@PathVariable Long id) {
        return ApiResponse.<LecturerResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin giảng viên thành công")
                .result(lecturerService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<LecturerResponse> updateLecturer(@PathVariable Long id, @RequestBody LecturerUpdateRequest req) {
        return ApiResponse.<LecturerResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật giảng viên thành công")
                .result(lecturerService.updateLecturer(id, req))
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteLecturer(@PathVariable Long id) {
        lecturerService.deleteLecturer(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Xoá giảng viên thành công")
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<LecturerResponse>> getAllLecturers(
            @ModelAttribute LecturerFilterRequest filter,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort) {
        return ApiResponse.<PageResponse<LecturerResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách giảng viên thành công")
                .result(lecturerService.getAllLecturers(filter, page, size, sort))
                .build();
    }
}
