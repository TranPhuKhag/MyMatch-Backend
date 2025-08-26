package com.mymatch.specification;

import com.mymatch.dto.request.swap.SwapFilterRequest;
import com.mymatch.entity.Swap;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SwapSpecification {
    public static Specification<Swap> withFilter(SwapFilterRequest f) {
        return (root, query, cb) -> {
            List<Predicate> ps = new ArrayList<>();

            if (f.getRequestFromId() != null) {
                ps.add(cb.equal(root.get("requestFrom").get("id"), f.getRequestFromId()));
            }
            if (f.getRequestToId() != null) {
                ps.add(cb.equal(root.get("requestTo").get("id"), f.getRequestToId()));
            }
            if (f.getStudentFromId() != null) {
                ps.add(cb.equal(root.get("studentFrom").get("id"), f.getStudentFromId()));
            }
            if (f.getStudentToId() != null) {
                ps.add(cb.equal(root.get("studentTo").get("id"), f.getStudentToId()));
            }
            if (f.getAnyStudentId() != null) {
                ps.add(cb.or(
                        cb.equal(root.get("studentFrom").get("id"), f.getAnyStudentId()),
                        cb.equal(root.get("studentTo").get("id"), f.getAnyStudentId())
                ));
            }
            if (f.getMode() != null) {
                ps.add(cb.equal(root.get("mode"), f.getMode()));
            }
            if (f.getStatus() != null) {
                ps.add(cb.equal(root.get("status"), f.getStatus()));
            }

            return cb.and(ps.toArray(new Predicate[0]));
        };
    }
}
