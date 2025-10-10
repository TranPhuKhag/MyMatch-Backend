package com.mymatch.repository;

import com.mymatch.entity.Campus;
import com.mymatch.entity.Lecturer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LecturerRepository extends JpaRepository<Lecturer, Long>, JpaSpecificationExecutor<Lecturer> {
    boolean existsByCodeAndCampus(String code, Campus campus);

    Optional<Lecturer> findByCode(String code);

    @Query(value = "SELECT l FROM Lecturer l JOIN l.reviews r WHERE r.student.user.id = :userId")
    Page<Lecturer> findLecturersReviewedByUser(@Param("userId") Long userId, Pageable pageable);

}
