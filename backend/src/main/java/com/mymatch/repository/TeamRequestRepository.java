package com.mymatch.repository;

import com.mymatch.entity.TeamRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRequestRepository extends JpaRepository<TeamRequest, Long>, JpaSpecificationExecutor<TeamRequest> {
}
