package com.mymatch.repository;

import com.mymatch.entity.Swap;
import com.mymatch.entity.SwapRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SwapRepository extends JpaRepository<Swap, Long>, JpaSpecificationExecutor<Swap> {
    Optional<Swap> findByIdAndStudentFromIdOrStudentToId(Long id, Long studentFromId, Long studentToId);
}
