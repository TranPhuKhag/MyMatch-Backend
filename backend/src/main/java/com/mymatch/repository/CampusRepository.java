package com.mymatch.repository;

import com.mymatch.entity.Campus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampusRepository extends JpaRepository<Campus, Long> {
    boolean existsByNameAndUniversityId(String name, Long universityId);
    Optional<Campus> findByIdAndUniversityId(Long campusId, Long universityId);
}