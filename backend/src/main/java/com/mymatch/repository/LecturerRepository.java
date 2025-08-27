package com.mymatch.repository;

import com.mymatch.entity.Campus;
import com.mymatch.entity.Lecturer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface LecturerRepository extends JpaRepository<Lecturer, Long>, JpaSpecificationExecutor<Lecturer> {
    boolean existsByCodeAndCampus(String code, Campus campus);
    Optional<Lecturer> findByCode(String code);
}
