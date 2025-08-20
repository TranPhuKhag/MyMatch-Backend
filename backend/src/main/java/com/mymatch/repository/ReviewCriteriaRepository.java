package com.mymatch.repository;

import com.mymatch.entity.ReviewCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewCriteriaRepository extends JpaRepository<ReviewCriteria, Long>, JpaSpecificationExecutor<ReviewCriteria> {

}