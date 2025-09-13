package com.mymatch.service;

import com.mymatch.dto.request.plan.PlanCreationRequest;
import com.mymatch.dto.request.plan.PlanUpdateRequest;
import com.mymatch.dto.response.plan.PlanResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface PlanService {
//    @PreAuthorize("hasAuthority('plan:create')")
    PlanResponse createPlan(PlanCreationRequest request);

//    @PreAuthorize("hasAuthority('plan:update')")
    PlanResponse updatePlan(Long id, PlanUpdateRequest request);

//    @PreAuthorize("hasAuthority('plan:delete')")
    void deletePlan(Long id);

    List<PlanResponse> getAll();
}
