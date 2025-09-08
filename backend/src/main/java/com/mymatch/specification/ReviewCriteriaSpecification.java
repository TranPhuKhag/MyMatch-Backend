package com.mymatch.specification;

import com.mymatch.dto.request.reviewcriteria.ReviewCriteriaFilter;
import com.mymatch.entity.ReviewCriteria;
import org.springframework.data.jpa.domain.Specification;

public class ReviewCriteriaSpecification {

    public static Specification<ReviewCriteria> filter(ReviewCriteriaFilter filter) {
        return (root, query, cb) -> {
            Specification<ReviewCriteria> spec = Specification.where(null);

            if (filter.getName() != null && !filter.getName().isBlank()) {
                spec = spec.and((r, q, b) ->
                        b.like(b.lower(r.get("name")), "%" + filter.getName().toLowerCase() + "%"));
            }

            if (filter.getType() != null) {
                spec = spec.and((r, q, b) ->
                        b.equal(r.get("type"), filter.getType()));
            }

            return spec.toPredicate(root, query, cb);
        };
    }
}
