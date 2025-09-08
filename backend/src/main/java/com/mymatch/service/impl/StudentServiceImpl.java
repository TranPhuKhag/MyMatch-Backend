package com.mymatch.service.impl;

import com.mymatch.dto.request.student.StudentCreationRequest;
import com.mymatch.dto.request.student.StudentFilterRequest;
import com.mymatch.dto.request.student.StudentUpdateRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.student.StudentResponse;
import com.mymatch.entity.Campus;
import com.mymatch.entity.Student;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.StudentMapper;
import com.mymatch.repository.CampusRepository;
import com.mymatch.repository.StudentRepository;
import com.mymatch.repository.UniversityRepository;
import com.mymatch.service.StudentService;
import com.mymatch.specification.StudentSpecification;
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
public class StudentServiceImpl implements StudentService {
    StudentRepository studentRepository;
    CampusRepository campusRepository;
    StudentMapper studentMapper;
    UniversityRepository universityRepository;

    @Override
    public StudentResponse getById(Long id) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND));
        return studentMapper.toResponse(student);
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentUpdateRequest req) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND));

        studentMapper.update(student, req);

        if (req.getCampusId() != null) {
            Campus campus = campusRepository.findById(req.getCampusId())
                    .orElseThrow(() -> new AppException(ErrorCode.CAMPUS_NOT_FOUND));
            student.setCampus(campus);
        }

        student = studentRepository.save(student);
        return studentMapper.toResponse(student);
    }

    @Override
    public StudentResponse deleteStudent(Long id) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND));
        studentRepository.delete(student);
        return studentMapper.toResponse(student);
    }

    @Override
    public PageResponse<StudentResponse> getAllStudents(StudentFilterRequest req) {
        var spec = StudentSpecification.withFilter(req);

        var sortBy = (req.getSortBy() == null || req.getSortBy().isBlank()) ? "id" : req.getSortBy();
        Pageable pageable = PageRequest.of(
                Math.max(req.getPage() - 1, 0),
                req.getSize(),
                Sort.by(sortBy)
        );

        Page<Student> pages = studentRepository.findAll(spec, pageable);
        List<StudentResponse> data = pages.getContent().stream()
                .map(studentMapper::toResponse)
                .collect(Collectors.toList());

        return PageResponse.<StudentResponse>builder()
                .data(data)
                .pageSize(pages.getSize())
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .currentPage(req.getPage())
                .build();
    }
}
