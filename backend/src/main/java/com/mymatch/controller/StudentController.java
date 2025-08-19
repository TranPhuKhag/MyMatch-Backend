package com.mymatch.controller;

import com.mymatch.dto.request.student.StudentCreationRequest;
import com.mymatch.dto.request.student.StudentFilterRequest;
import com.mymatch.dto.request.student.StudentUpdateRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.student.StudentResponse;
import com.mymatch.service.StudentService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentController {

    StudentService studentService;

    @PostMapping
    public ApiResponse<StudentResponse> createStudent(@RequestBody StudentCreationRequest req) {
        return ApiResponse.<StudentResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tạo sinh viên thành công")
                .result(studentService.createStudent(req))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<StudentResponse> getById(@PathVariable Long id) {
        return ApiResponse.<StudentResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin sinh viên thành công")
                .result(studentService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<StudentResponse> updateStudent(@PathVariable Long id, @RequestBody StudentUpdateRequest req) {
        return ApiResponse.<StudentResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin sinh viên thành công")
                .result(studentService.updateStudent(id, req))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<StudentResponse> deleteStudent(@PathVariable Long id) {
        return ApiResponse.<StudentResponse>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Xoá sinh viên thành công")
                .result(studentService.deleteStudent(id))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<StudentResponse>> getAllStudents(@RequestBody StudentFilterRequest req) {
        return ApiResponse.<PageResponse<StudentResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách sinh viên thành công")
                .result(studentService.getAllStudents(req))
                .build();
    }
}
