package com.mymatch.service.impl;

import com.mymatch.dto.request.university.UniversityCreationRequest;
import com.mymatch.dto.request.university.UniversityUpdateRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.university.UniversityResponse;
import com.mymatch.entity.University;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.UniversityMapper;
import com.mymatch.repository.UniversityRepository;
import com.mymatch.service.UniversityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UniversityServiceImpl implements UniversityService {
    UniversityRepository universityRepository;
    UniversityMapper universityMapper;

    @Override
    public UniversityResponse createUniversity(UniversityCreationRequest req) {
        if (universityRepository.existsByName(req.getName())) throw new AppException(ErrorCode.UNIVERSITY_EXISTED);
        University university = universityMapper.toEntity(req);
        university = universityRepository.save(university);
        return universityMapper.toResponse(university);
    }

    @Override
    public UniversityResponse getById(Long id) {
        var university = universityRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.UNIVERSITY_NOT_FOUND));
        return universityMapper.toResponse(university);
    }

    @Override
    public UniversityResponse updateUniversity(Long id, UniversityUpdateRequest req) {
        var university = universityRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.UNIVERSITY_NOT_FOUND));
        universityMapper.update(university, req);
        return universityMapper.toResponse(universityRepository.save(university));
    }

    @Override
    public UniversityResponse deleteUniversity(Long id) {
        University university = universityRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.UNIVERSITY_NOT_FOUND));
        universityRepository.delete(university);
        return universityMapper.toResponse(university);    }

    @Override
    public PageResponse<UniversityResponse> getAllUniversities(int page, int size, String sort) {
        if (sort == null || sort.isEmpty()) {
            sort = "id";
        }
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort));
        Page<University> pages = universityRepository.findAll(pageable);
        List<UniversityResponse> data = pages.getContent().stream()
                .map(universityMapper::toResponse)
                .collect(Collectors.toList());
        return PageResponse.<UniversityResponse>builder()
                .data(data)
                .pageSize(pages.getSize())
                .totalPages(pages.getNumber())
                .totalElements(pages.getTotalElements())
                .currentPage(page)
                .build();
    }
}
