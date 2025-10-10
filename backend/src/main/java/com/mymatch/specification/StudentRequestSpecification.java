package com.mymatch.specification;

import com.mymatch.dto.request.studentrequest.StudentRequestFilterRequest;
import com.mymatch.entity.StudentRequest;
import org.springframework.data.jpa.domain.Specification;

public class StudentRequestSpecification {

    public static Specification<StudentRequest> byFilter(StudentRequestFilterRequest f) {
        return (root, cq, cb) -> {
            var p = cb.conjunction();

            if (f.getStudentId() != null) {
                p = cb.and(p, cb.equal(root.get("student").get("id"), f.getStudentId()));
            }
            if (f.getCourseId() != null) {
                p = cb.and(p, cb.equal(root.get("course").get("id"), f.getCourseId()));
            }
            if (f.getSemesterId() != null) {
                p = cb.and(p, cb.equal(root.get("semester").get("id"), f.getSemesterId()));
            }
            if (f.getCampusId() != null) {
                p = cb.and(p, cb.equal(root.get("campus").get("id"), f.getCampusId()));
            }
            if (f.getStatus() != null) {
                p = cb.and(p, cb.equal(root.get("status"), f.getStatus()));
            }
            if (f.getClassCode() != null && !f.getClassCode().isBlank()) {
                p = cb.and(p, cb.equal(cb.lower(root.get("classCode")), f.getClassCode().toLowerCase()));
            }
            return p;
        };
    }
}
