package com.mymatch.repository;

import com.mymatch.entity.Campus;
import com.mymatch.entity.Course;
import com.mymatch.entity.Lecturer;
import com.mymatch.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    boolean existsByCodeAndUniversity(String code, University university);
}
