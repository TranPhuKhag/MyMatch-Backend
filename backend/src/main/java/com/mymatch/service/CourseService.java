package com.mymatch.service;

import com.mymatch.dto.request.course.CourseCreationRequest;
import com.mymatch.dto.request.course.CourseUpdateRequest;
import com.mymatch.dto.request.course.CourseFilterRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.course.CourseResponse;
import org.springframework.security.access.prepost.PreAuthorize;

public interface CourseService {
//    @PreAuthorize("hasAuthority('course:create')")
    CourseResponse createCourse(CourseCreationRequest req);

    CourseResponse getById(Long id);

    @PreAuthorize("hasAuthority('course:update')")
    CourseResponse updateCourse(Long id, CourseUpdateRequest req);

    @PreAuthorize("hasAuthority('course:delete')")
    void deleteCourse(Long id);

    PageResponse<CourseResponse> getAllCourses(CourseFilterRequest filter, int page, int size, String sort);
}
