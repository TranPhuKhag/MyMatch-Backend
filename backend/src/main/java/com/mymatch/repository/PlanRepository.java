package com.mymatch.repository;

import com.mymatch.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
}

