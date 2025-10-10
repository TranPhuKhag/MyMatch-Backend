package com.mymatch.repository;

import com.mymatch.entity.LecturerCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LecturerCourseRepository extends JpaRepository<LecturerCourse, Long> {
    boolean existsByLecturer_IdAndCourse_Id(Long lecturerId, Long courseId);
    List<LecturerCourse> findByLecturer_Id(Long lecturerId);
}
