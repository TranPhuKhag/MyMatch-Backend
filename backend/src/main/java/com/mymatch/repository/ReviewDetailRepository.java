package com.mymatch.repository;

import com.mymatch.entity.ReviewCriteria;
import com.mymatch.entity.ReviewDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewDetailRepository extends JpaRepository<ReviewDetail, Long>, JpaSpecificationExecutor<ReviewDetail> {

}