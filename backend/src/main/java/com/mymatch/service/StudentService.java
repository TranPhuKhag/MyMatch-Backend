package com.mymatch.service;

import com.mymatch.dto.request.student.StudentFilterRequest;
import com.mymatch.dto.request.student.StudentUpdateRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.student.StudentResponse;
import org.springframework.security.access.prepost.PreAuthorize;

public interface StudentService {

    StudentResponse updateStudent(Long id, StudentUpdateRequest req);

    @PreAuthorize("hasAuthority('student:delete')")
    StudentResponse deleteStudent(Long id);

    StudentResponse getById(Long id);

    @PreAuthorize("hasAuthority('student:read')")
    PageResponse<StudentResponse> getAllStudents(StudentFilterRequest req);
}
