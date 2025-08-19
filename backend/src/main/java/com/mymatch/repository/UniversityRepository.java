package com.mymatch.repository;

import com.mymatch.entity.University;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
    boolean existsByName(@NotBlank String name);
}