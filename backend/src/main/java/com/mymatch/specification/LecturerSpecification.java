package com.mymatch.specification;


import com.mymatch.dto.request.lecturer.LecturerFilterRequest;
import com.mymatch.entity.Lecturer;
import org.springframework.data.jpa.domain.Specification;

public class LecturerSpecification {

    public static Specification<Lecturer> hasCampusId(Long campusId) {
        return (root, query, cb) ->
                campusId == null ? cb.conjunction() :
                        cb.equal(root.get("campus").get("id"), campusId);
    }

    public static Specification<Lecturer> nameContains(String name) {
        return (root, query, cb) ->
                (name == null || name.isEmpty()) ? cb.conjunction() :
                        cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Lecturer> codeContains(String code) {
        return (root, query, cb) ->
                (code == null || code.isEmpty()) ? cb.conjunction() :
                        cb.like(cb.lower(root.get("code")), "%" + code.toLowerCase() + "%");
    }

    public static Specification<Lecturer> buildSpec(LecturerFilterRequest filter) {
        return Specification
                .where(hasCampusId(filter.getCampusId()))
                .and(nameContains(filter.getName()))
                .and(codeContains(filter.getCode()));
    }
}
