package com.mymatch.service;

import com.mymatch.dto.request.student.StudentCreationRequest;
import com.mymatch.dto.request.student.StudentFilterRequest;
import com.mymatch.dto.request.student.StudentUpdateRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.student.StudentResponse;
import com.mymatch.dto.response.university.UniversityResponse;
import org.springframework.security.access.prepost.PreAuthorize;

public interface StudentService {
    StudentResponse createStudent(StudentCreationRequest req);

    StudentResponse updateStudent(Long id, StudentUpdateRequest req);

    @PreAuthorize("hasAuthority('student:delete')")
    StudentResponse deleteStudent(Long id);

    StudentResponse getById(Long id);

    @PreAuthorize("hasAuthority('student:read')")
    PageResponse<StudentResponse> getAllStudents(StudentFilterRequest req);
}
