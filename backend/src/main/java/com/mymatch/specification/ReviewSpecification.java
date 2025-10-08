package com.mymatch.specification;

import com.mymatch.dto.request.review.ReviewFilterRequest;
import com.mymatch.entity.Review;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ReviewSpecification {


    public static Specification<Review> byFilter(ReviewFilterRequest f) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (f == null) return cb.and(predicates.toArray(new Predicate[0]));

            if (f.getLecturerId() != null) {
                predicates.add(cb.equal(root.get("lecturer").get("id"), f.getLecturerId()));
            }
            if (f.getCourseId() != null) {
                predicates.add(cb.equal(root.get("course").get("id"), f.getCourseId()));
            }
            if (f.getStudentId() != null) {
                predicates.add(cb.equal(root.get("student").get("id"), f.getStudentId()));
            }
            if (f.getSemesterId() != null) {
                predicates.add(cb.equal(root.get("semester").get("id"), f.getSemesterId()));
            }
            if (f.getIsVerified() != null) {
                predicates.add(cb.equal(root.get("isVerified"), f.getIsVerified()));
            }
            if (f.getIsAnonymous() != null) {
                predicates.add(cb.equal(root.get("isAnonymous"), f.getIsAnonymous()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
