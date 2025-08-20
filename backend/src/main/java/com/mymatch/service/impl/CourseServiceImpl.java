package com.mymatch.service.impl;

import com.mymatch.dto.request.course.CourseCreationRequest;
import com.mymatch.dto.request.course.CourseFilterRequest;
import com.mymatch.dto.request.course.CourseUpdateRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.course.CourseResponse;
import com.mymatch.entity.Course;
import com.mymatch.entity.University;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.CourseMapper;
import com.mymatch.repository.CourseRepository;
import com.mymatch.repository.UniversityRepository;
import com.mymatch.service.CourseService;
import com.mymatch.specification.CourseSpecification;
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
public class CourseServiceImpl implements CourseService {

    CourseRepository courseRepository;
    UniversityRepository universityRepository;
    CourseMapper courseMapper;

    @Override
    public CourseResponse createCourse(CourseCreationRequest req) {
        University university = universityRepository.findById(req.getUniversityId())
                .orElseThrow(() -> new AppException(ErrorCode.UNIVERSITY_NOT_FOUND));

        boolean isExisted = courseRepository.existsByCodeAndUniversity(req.getCode(), university);
        if (isExisted) {
            throw new AppException(ErrorCode.COURSE_EXISTED);
        }

        Course course = courseMapper.toCourse(req, university);
        course = courseRepository.save(course);
        return courseMapper.toCourseResponse(course);
    }

    @Override
    public CourseResponse getById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
        return courseMapper.toCourseResponse(course);
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseUpdateRequest request) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));

        courseMapper.update(course, request);
        return courseMapper.toCourseResponse(course);
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
        courseRepository.delete(course);
    }

    @Override
    public PageResponse<CourseResponse> getAllCourses(CourseFilterRequest filter, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(
                page - 1,
                size,
                Sort.by(sort == null ? "name" : sort)
        );

        var spec = CourseSpecification.buildSpec(filter);
        Page<Course> pages = courseRepository.findAll(spec, pageable);

        List<CourseResponse> responses = new ArrayList<>();
        for (Course course : pages.getContent()) {
            responses.add(courseMapper.toCourseResponse(course));
        }

        return PageResponse.<CourseResponse>builder()
                .data(responses)
                .pageSize(pages.getSize())
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .currentPage(page)
                .build();
    }
}
