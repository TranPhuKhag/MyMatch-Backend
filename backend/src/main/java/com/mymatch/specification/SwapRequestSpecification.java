package com.mymatch.specification;

import com.mymatch.dto.request.swaprequest.SwapRequestFilterRequest;
import com.mymatch.entity.SwapRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SwapRequestSpecification {
    public static Specification<SwapRequest> withFilter(SwapRequestFilterRequest f) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (f.getStudentId() != null) {
                predicates.add(cb.equal(root.get("student").get("id"), f.getStudentId()));
            }
            if (f.getCourseId() != null) {
                predicates.add(cb.equal(root.get("course").get("id"), f.getCourseId()));
            }
            if (f.getLecturerFromId() != null) {
                predicates.add(cb.equal(root.get("lecturerFrom").get("id"), f.getLecturerFromId()));
            }
            if (f.getLecturerToId() != null) {
                predicates.add(cb.equal(root.get("lecturerTo").get("id"), f.getLecturerToId()));
            }
            if (f.getSlotFrom() != null) {
                predicates.add(cb.equal(root.get("slotFrom"), f.getSlotFrom()));
            }
            if (f.getSlotTo() != null) {
                predicates.add(cb.equal(root.get("slotTo"), f.getSlotTo()));
            }
            if (f.getVisibility() != null) {
                predicates.add(cb.equal(root.get("visibility"), f.getVisibility()));
            }
            if (f.getFromClass() != null && !f.getFromClass().isBlank()) {
                predicates.add(cb.equal(root.get("fromClass"), f.getFromClass()));
            }
            if (f.getTargetClass() != null && !f.getTargetClass().isBlank()) {
                predicates.add(cb.equal(root.get("targetClass"), f.getTargetClass()));
            }
            if (f.getStatuses() != null && !f.getStatuses().isEmpty()) {
                predicates.add(root.get("status").in(f.getStatuses()));
            }
            if(Boolean.FALSE.equals(f.getIncludeExpired())) {
                predicates.add(
                        cb.or(
                                cb.greaterThan(root.get("expiresAt"), java.time.LocalDateTime.now()),
                                cb.isNull(root.get("expiresAt"))
                        )
                );
            };
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
