package com.mymatch.specification;

import com.mymatch.dto.request.material.MaterialFilter;
import com.mymatch.entity.Material;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MaterialSpecification {

    public static Specification<Material> withFilter(MaterialFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(filter.getName())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + filter.getName().toLowerCase() + "%"
                ));
            }

            // Filter theo description (like)
            if (StringUtils.hasText(filter.getDescription())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("description")),
                        "%" + filter.getDescription().toLowerCase() + "%"
                ));
            }

            // Filter theo lecturerId
            if (filter.getLecturerId() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("lecturer").get("id"),
                        filter.getLecturerId()
                ));
            }

            // Filter theo ownerId
            if (filter.getOwnerId() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("owner").get("id"),
                        filter.getOwnerId()
                ));
            }

            // Filter theo courseId
            if (filter.getCourseId() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("course").get("id"),
                        filter.getCourseId()
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}