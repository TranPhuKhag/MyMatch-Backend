package com.mymatch.repository;

import com.mymatch.entity.Campus;
import com.mymatch.entity.Lecturer;
import com.mymatch.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SemesterRepository extends JpaRepository<Semester, Long>, JpaSpecificationExecutor<Semester> {

    List<Semester> findByUniversityId(Long universityId);
}
