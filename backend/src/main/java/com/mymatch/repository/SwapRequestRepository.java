package com.mymatch.repository;

import com.mymatch.entity.SwapRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SwapRequestRepository extends JpaRepository<SwapRequest, Long>, JpaSpecificationExecutor<SwapRequest> {
    Optional<SwapRequest> findByIdAndStudentId(Long id, Long studentId);
}
