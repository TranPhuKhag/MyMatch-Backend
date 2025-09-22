package com.mymatch.specification;

import com.mymatch.dto.request.team.TeamFilterRequest;
import com.mymatch.entity.Team;
import org.springframework.data.jpa.domain.Specification;

public class TeamSpecification {
    public static Specification<Team> byFilter(TeamFilterRequest f) {
        return (root, cq, cb) -> {
            var p = cb.conjunction();
            if (f.getSemesterId() != null) {
                p = cb.and(p, cb.equal(root.get("semester").get("id"), f.getSemesterId()));
            }
            if (f.getCampusId() != null) {
                p = cb.and(p, cb.equal(root.get("campus").get("id"), f.getCampusId()));
            }
            if (f.getCourseId() != null) {
                p = cb.and(p, cb.equal(root.get("course").get("id"), f.getCourseId()));
            }
            if (f.getKeyword() != null && !f.getKeyword().isBlank()) {
                var like = "%" + f.getKeyword().toLowerCase() + "%";
                p = cb.and(p, cb.or(
                        cb.like(cb.lower(root.get("name")), like),
                        cb.like(cb.lower(root.get("description")), like)
                ));
            }
            return p;
        };
    }
}
