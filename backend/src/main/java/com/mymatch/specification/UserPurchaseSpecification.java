package com.mymatch.specification;

import com.mymatch.dto.request.purchase.UserPurchaseFilterRequest;
import com.mymatch.entity.UserPurchase;
import com.mymatch.enums.PurchaseStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserPurchaseSpecification {

    public static Specification<UserPurchase> byFilter(UserPurchaseFilterRequest f) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (f == null) return cb.and(predicates.toArray(new Predicate[0]));

            if (f.getUserId() != null) {
                predicates.add(cb.equal(root.get("user").get("id"), f.getUserId()));
            }
            if (f.getPlanId() != null) {
                predicates.add(cb.equal(root.get("plan").get("id"), f.getPlanId()));
            }
            if (f.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), f.getStatus()));
            }
            if (f.getPurchasedFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("purchaseDate"), f.getPurchasedFrom()));
            }
            if (f.getPurchasedTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("purchaseDate"), f.getPurchasedTo()));
            }

            if (f.getExpiryFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("expiryDate"), f.getExpiryFrom()));
            }
            if (f.getExpiryTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("expiryDate"), f.getExpiryTo()));
            }

            if (Boolean.TRUE.equals(f.getActiveNow())) {
                predicates.add(cb.equal(root.get("status"), PurchaseStatus.ACTIVE));
                predicates.add(cb.greaterThanOrEqualTo(root.get("expiryDate"), LocalDateTime.now()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
