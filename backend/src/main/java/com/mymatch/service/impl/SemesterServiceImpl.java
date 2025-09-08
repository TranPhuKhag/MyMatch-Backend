package com.mymatch.service.impl;

import com.mymatch.dto.response.semester.SemesterResponse;
import com.mymatch.entity.Semester;
import com.mymatch.mapper.SemesterMapper;
import com.mymatch.repository.SemesterRepository;
import com.mymatch.service.SemesterService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SemesterServiceImpl implements SemesterService {
    SemesterRepository semesterRepository;
    SemesterMapper semesterMapper;

    @Override
    public List<SemesterResponse> getSemestersByUniversityId(Long universityId) {
        List<Semester> semesters = semesterRepository.findByUniversityId(universityId);
        return semesters.stream().map(semesterMapper::toResponse).toList();
    }
}
