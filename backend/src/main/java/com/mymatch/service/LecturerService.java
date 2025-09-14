package com.mymatch.service;

import com.mymatch.dto.request.lecturer.LecturerCreationRequest;
import com.mymatch.dto.request.lecturer.LecturerFilterRequest;
import com.mymatch.dto.request.lecturer.LecturerUpdateRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.lecturer.LecturerResponse;
import org.springframework.security.access.prepost.PreAuthorize;

public interface LecturerService {
    @PreAuthorize("hasAuthority('lecturer:create')")
    LecturerResponse createLecturer(LecturerCreationRequest req);

    LecturerResponse getById(Long id);

    @PreAuthorize("hasAuthority('lecturer:update')")
    LecturerResponse updateLecturer(Long id, LecturerUpdateRequest req);

    @PreAuthorize("hasAuthority('lecturer:delete')")
    void deleteLecturer(Long id);

    PageResponse<LecturerResponse> getAllLecturers(LecturerFilterRequest filter, int page, int size, String sort);
}
