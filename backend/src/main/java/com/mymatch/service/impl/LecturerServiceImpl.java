package com.mymatch.service.impl;

import com.mymatch.dto.request.lecturer.LecturerCreationRequest;
import com.mymatch.dto.request.lecturer.LecturerFilterRequest;
import com.mymatch.dto.request.lecturer.LecturerUpdateRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.lecturer.LecturerResponse;
import com.mymatch.entity.Campus;
import com.mymatch.entity.Lecturer;
import com.mymatch.entity.Tag;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.LecturerMapper;
import com.mymatch.repository.CampusRepository;
import com.mymatch.repository.LecturerRepository;
import com.mymatch.repository.TagRepository;
import com.mymatch.service.LecturerService;
import com.mymatch.specification.LecturerSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LecturerServiceImpl implements LecturerService {

    LecturerRepository lecturerRepository;
    TagRepository tagRepository;
    CampusRepository campusRepository;
    LecturerMapper lecturerMapper;


    @Override
    public LecturerResponse createLecturer(LecturerCreationRequest request) {
        Campus campus = campusRepository.findById(request.getCampusId())
                .orElseThrow(() -> new AppException(ErrorCode.CAMPUS_NOT_FOUND));
        boolean isExist = lecturerRepository.existsByCodeAndCampus(request.getCode(), campus);
        if (isExist) {
            throw new AppException(ErrorCode.LECTURER_EXISTED);
        }
        Lecturer lecturer = lecturerMapper.toLecturer(request, campus);
        lecturer = lecturerRepository.save(lecturer);
        return lecturerMapper.toLecturerResponse(lecturer, null, 0);
    }

    @Override
    public LecturerResponse getById(Long id) {
        Lecturer lecturer = lecturerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LECTURER_NOT_FOUND));
        int reviewCount = lecturer.getReviews() != null ? lecturer.getReviews().size() : 0;
        return lecturerMapper.toLecturerResponse(lecturer, lecturer.getReviews(), reviewCount);
    }


    @Override
    public LecturerResponse updateLecturer(Long id, LecturerUpdateRequest request) {
        Campus campus = null;
        if (request.getCampusId() != null) {
            campus = campusRepository.findById(request.getCampusId())
                    .orElseThrow(() -> new AppException(ErrorCode.CAMPUS_NOT_FOUND));
        }
        Lecturer lecturer = lecturerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LECTURER_NOT_FOUND));
        List<Tag> tags = tagRepository.findByIdIn(request.getTagIds());
        lecturerMapper.update(lecturer, request, campus, tags);
        int reviewCount = lecturer.getReviews() != null ? lecturer.getReviews().size() : 0;
        return lecturerMapper.toLecturerResponse(lecturer, lecturer.getReviews(), reviewCount);
    }

    @Override
    public void deleteLecturer(Long id) {
        Lecturer lecturer = lecturerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LECTURER_NOT_FOUND));
        lecturerRepository.delete(lecturer);
    }

    @Override
    public PageResponse<LecturerResponse> getAllLecturers(LecturerFilterRequest filter, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(
                page - 1,
                size,
                Sort.by("name")
        );
        var spec = LecturerSpecification.buildSpec(filter);
        var pages = lecturerRepository.findAll(spec, pageable);
        List<LecturerResponse> lecturerResponses = new ArrayList<>();
        for (Lecturer lecturer : pages.getContent()) {
            int reviewCount = lecturer.getReviews() != null ? lecturer.getReviews().size() : 0;
            LecturerResponse response = lecturerMapper.toLecturerResponse(lecturer, null, reviewCount);
            lecturerResponses.add(response);
        }

        return PageResponse.<LecturerResponse>builder()
                .data(lecturerResponses)
                .pageSize(pages.getSize())
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .currentPage(page)
                .build();
    }
}
