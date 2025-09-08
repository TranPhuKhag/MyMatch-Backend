package com.mymatch.specification;

import com.mymatch.dto.request.course.CourseFilterRequest;
import com.mymatch.entity.Course;
import org.springframework.data.jpa.domain.Specification;

public class CourseSpecification {

    public static Specification<Course> buildSpec(CourseFilterRequest filter) {
        return Specification.where(hasCode(filter.getCode()))
                .and(hasName(filter.getName()))
                .and(hasUniversity(filter.getUniversityId()));
    }

    private static Specification<Course> hasCode(String code) {
        return (root, query, cb) -> {
            if (code == null || code.isBlank()) {
                return null;
            }
            // contains, case-insensitive
            return cb.like(cb.lower(root.get("code")), "%" + code.toLowerCase() + "%");
        };
    }

    private static Specification<Course> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) {
                return null;
            }
            // contains, case-insensitive
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    private static Specification<Course> hasUniversity(Long universityId) {
        return (root, query, cb) -> {
            if (universityId == null) {
                return null;
            }
            return cb.equal(root.get("university").get("id"), universityId);
        };
    }
}
