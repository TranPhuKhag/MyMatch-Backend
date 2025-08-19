package com.mymatch.service;

import com.mymatch.dto.request.campus.CampusCreationRequest;
import com.mymatch.dto.request.campus.CampusUpdateRequest;
import com.mymatch.dto.request.university.UniversityCreationRequest;
import com.mymatch.dto.request.university.UniversityUpdateRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.campus.CampusResponse;
import com.mymatch.dto.response.university.UniversityResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface CampusService {
    @PreAuthorize("hasAuthority('campus:create')")
    CampusResponse createCampus(CampusCreationRequest req);
    CampusResponse getById(Long id);
    @PreAuthorize("hasAuthority('campus:update')")
    CampusResponse updateCampus(Long id, CampusUpdateRequest req);
    @PreAuthorize("hasAuthority('campus:delete')")
    CampusResponse deleteCampus(Long id);
    PageResponse<CampusResponse> getAllCampuses(int page, int size, String sort, Long universityId);
}
