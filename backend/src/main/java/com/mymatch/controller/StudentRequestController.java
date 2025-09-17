package com.mymatch.controller;
import com.mymatch.dto.request.studentrequest.StudentRequestCreationRequest;
import com.mymatch.dto.request.studentrequest.StudentRequestFilterRequest;
import com.mymatch.dto.request.studentrequest.StudentRequestUpdateRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.studentrequest.StudentRequestResponse;
import com.mymatch.service.StudentRequestService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/student-requests")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class StudentRequestController {

    StudentRequestService studentRequestService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<StudentRequestResponse> createStudentRequest(
            @Validated @RequestBody StudentRequestCreationRequest request) {
        return ApiResponse.<StudentRequestResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tạo bài đăng tìm team thành công")
                .result(studentRequestService.createStudentRequest(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<StudentRequestResponse> updateStudentRequest(
            @PathVariable Long id,
            @Validated @RequestBody StudentRequestUpdateRequest request) {
        return ApiResponse.<StudentRequestResponse>builder()
                .message("Cập nhật bài đăng tìm team thành công")
                .result(studentRequestService.updateStudentRequest(id, request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<StudentRequestResponse> getStudentRequestById(@PathVariable Long id) {
        return ApiResponse.<StudentRequestResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin bài đăng tìm team thành công")
                .result(studentRequestService.getStudentRequestById(id))
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteStudentRequest(@PathVariable Long id) {
        studentRequestService.deleteStudentRequest(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Xoá bài đăng tìm team thành công")
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<StudentRequestResponse>> getAllStudentRequests(
            @ModelAttribute StudentRequestFilterRequest filterRequest,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") String sortDirection) {
        return ApiResponse.<PageResponse<StudentRequestResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách bài đăng tìm team thành công")
                .result(studentRequestService.getAllStudentRequests(filterRequest, page, size, sortBy, sortDirection))
                .build();
    }
}
