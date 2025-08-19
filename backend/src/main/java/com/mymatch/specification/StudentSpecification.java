package com.mymatch.specification;

import com.mymatch.dto.request.student.StudentFilterRequest;
import com.mymatch.entity.Student;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class StudentSpecification {
    public static Specification<Student> withFilter(StudentFilterRequest f) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (f.getCampusId() != null) {
                predicates.add(cb.equal(root.get("campus").get("id"), f.getCampusId()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}