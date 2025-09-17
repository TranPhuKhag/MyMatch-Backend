package com.mymatch.service;

import com.mymatch.dto.request.lecturercourse.LecturerCourseCreationRequest;
import com.mymatch.dto.response.lecturercourse.LecturerCourseResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface LecturerCourseService {
    @PreAuthorize("hasAuthority('lecturercourse:create')")
    LecturerCourseResponse assign(LecturerCourseCreationRequest req);
    List<LecturerCourseResponse> getByLecturerId(Long lecturerId);
}

