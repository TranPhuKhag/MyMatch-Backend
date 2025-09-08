package com.mymatch.repository;

import com.mymatch.entity.Swap;
import com.mymatch.entity.SwapRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SwapRepository extends JpaRepository<Swap, Long>, JpaSpecificationExecutor<Swap> {
    Optional<Swap> findByIdAndStudentFromIdOrStudentToId(Long id, Long studentFromId, Long studentToId);
    @Query("""
       select (count(s)>0) from Swap s
       where (s.requestFrom.id = :swapRequestCurrent and s.requestTo.id = :existingSwapRequest)
          or (s.requestFrom.id = :existingSwapRequest and s.requestTo.id = :swapRequestCurrent)
    """)
    boolean existsByPairEitherOrder(@Param("swapRequestCurrent") Long swapRequestCurrent, @Param("existingSwapRequest") Long existingSwapRequest);
}
