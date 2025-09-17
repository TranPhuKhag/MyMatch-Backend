package com.mymatch.service.impl;

import com.mymatch.dto.request.lecturercourse.LecturerCourseCreationRequest;
import com.mymatch.dto.response.lecturercourse.LecturerCourseResponse;
import com.mymatch.entity.Course;
import com.mymatch.entity.Lecturer;
import com.mymatch.entity.LecturerCourse;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.LecturerCourseMapper;
import com.mymatch.repository.CourseRepository;
import com.mymatch.repository.LecturerCourseRepository;
import com.mymatch.repository.LecturerRepository;
import com.mymatch.service.LecturerCourseService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LecturerCourseServiceImpl implements LecturerCourseService {
    LecturerCourseRepository lecturerCourseRepository;
    LecturerRepository lecturerRepository;
    CourseRepository courseRepository;
    LecturerCourseMapper lecturerCourseMapper;

    @Override
    public LecturerCourseResponse assign(LecturerCourseCreationRequest req) {
        Lecturer lecturer = lecturerRepository.findById(req.getLecturerId())
                .orElseThrow(() -> new AppException(ErrorCode.LECTURER_NOT_FOUND));
        Course course = courseRepository.findById(req.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));

        boolean exists = lecturerCourseRepository
                .existsByLecturer_IdAndCourse_Id(req.getLecturerId(), req.getCourseId());
        if (exists) throw new AppException(ErrorCode.LECTURER_COURSE_ALREADY_EXISTS);

        LecturerCourse lc = LecturerCourse.builder()
                .lecturer(lecturer)
                .course(course)
                .build();

        lc = lecturerCourseRepository.save(lc);
        return lecturerCourseMapper.toResponse(lc);
    }

    @Override
    public List<LecturerCourseResponse> getByLecturerId(Long lecturerId) {
        lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new AppException(ErrorCode.LECTURER_NOT_FOUND));

        var list = lecturerCourseRepository.findByLecturer_Id(lecturerId);
        return lecturerCourseMapper.toResponseList(list);
    }
}
