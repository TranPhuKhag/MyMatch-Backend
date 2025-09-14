package com.mymatch.repository;
import com.mymatch.entity.UserPurchase;
import com.mymatch.enums.PurchaseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserPurchaseRepository
        extends JpaRepository<UserPurchase, Long>, JpaSpecificationExecutor<UserPurchase> {


    List<UserPurchase> findByUserId(Long userId);

    List<UserPurchase> findByUserIdAndStatus(Long userId, PurchaseStatus status);

    List<UserPurchase> findByStatus(PurchaseStatus status);

    Optional<UserPurchase> findFirstByUserIdAndPlanIdAndStatus(Long userId, Long planId, PurchaseStatus status);
}