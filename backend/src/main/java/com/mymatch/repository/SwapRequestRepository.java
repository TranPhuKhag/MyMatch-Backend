package com.mymatch.repository;

import com.mymatch.entity.SwapRequest;

import com.mymatch.enums.ClassesSlot;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SwapRequestRepository extends JpaRepository<SwapRequest, Long>, JpaSpecificationExecutor<SwapRequest> {
    Optional<SwapRequest> findByIdAndStudentId(Long id, Long studentId);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT sr FROM SwapRequest sr
            WHERE sr.course.id = :courseId
              AND sr.fromClass = :fromClass
              AND sr.targetClass = :targetClass
              AND sr.lecturerFrom.id = :lecturerFromId
              AND sr.lecturerTo.id = :lecturerToId
              AND sr.slotFrom = :slotFrom
              AND sr.slotTo = :slotTo
              AND sr.student.id <> :studentId
            AND sr.status = 'SENT'
            """)
    Optional<SwapRequest> findBySwapRequest(
            Long courseId,
            String fromClass,
            String targetClass,
            Long lecturerFromId,
            Long lecturerToId,
            ClassesSlot slotFrom,
            ClassesSlot slotTo,
            Long studentId

    );

}
