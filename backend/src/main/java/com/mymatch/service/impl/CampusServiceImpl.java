package com.mymatch.service.impl;

import com.mymatch.dto.request.campus.CampusCreationRequest;
import com.mymatch.dto.request.campus.CampusUpdateRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.campus.CampusResponse;
import com.mymatch.entity.Campus;
import com.mymatch.entity.University;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.CampusMapper;
import com.mymatch.repository.CampusRepository;
import com.mymatch.repository.UniversityRepository;
import com.mymatch.service.CampusService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CampusServiceImpl implements CampusService {

    CampusRepository campusRepository;
    UniversityRepository universityRepository;
    CampusMapper campusMapper;

    @Override
    public CampusResponse createCampus(CampusCreationRequest req) {
        University uni = universityRepository.findById(req.getUniversityId())
                .orElseThrow(() -> new AppException(ErrorCode.UNIVERSITY_NOT_FOUND));

        if (campusRepository.existsByNameAndUniversityId(req.getName(), req.getUniversityId()))
            throw new AppException(ErrorCode.CAMPUS_EXISTED);

        Campus campus = campusMapper.toEntity(req);
        campus.setUniversity(uni);
        campus = campusRepository.save(campus);
        return campusMapper.toResponse(campus);
    }

    @Override
    public CampusResponse getById(Long id) {
        var campus = campusRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CAMPUS_NOT_FOUND));
        return campusMapper.toResponse(campus);
    }

    @Override
    public CampusResponse updateCampus(Long id, CampusUpdateRequest req) {
        var campus = campusRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CAMPUS_NOT_FOUND));

        campusMapper.update(campus, req);

        if (req.getUniversityId() != null) {
            University uni = universityRepository.findById(req.getUniversityId())
                    .orElseThrow(() -> new AppException(ErrorCode.UNIVERSITY_NOT_FOUND));
            campus.setUniversity(uni);
        }

        campus = campusRepository.save(campus);
        return campusMapper.toResponse(campus);
    }

    @Override
    public CampusResponse deleteCampus(Long id) {
        var campus = campusRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CAMPUS_NOT_FOUND));
        campusRepository.delete(campus);
        return campusMapper.toResponse(campus);
    }

    @Override
    public PageResponse<CampusResponse> getAllCampuses(int page, int size, String sort, Long universityId) {
        if (sort == null || sort.isEmpty()) sort = "id";
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort));

        Page<Campus> pages = campusRepository.findAll(pageable);

        List<CampusResponse> data = pages.getContent().stream()
                .map(campusMapper::toResponse)
                .collect(Collectors.toList());

        return PageResponse.<CampusResponse>builder()
                .data(data)
                .pageSize(pages.getSize())
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .currentPage(page)
                .build();
    }
}
