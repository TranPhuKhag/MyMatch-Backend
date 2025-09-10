package com.mymatch.service;

import com.mymatch.dto.response.semester.SemesterResponse;

import java.util.List;

public interface SemesterService {
    List<SemesterResponse> getSemestersByUniversityId(Long universityId);
}
