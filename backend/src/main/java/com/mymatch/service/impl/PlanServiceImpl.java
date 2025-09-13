package com.mymatch.service.impl;

import com.mymatch.dto.request.plan.PlanCreationRequest;
import com.mymatch.dto.request.plan.PlanUpdateRequest;
import com.mymatch.dto.response.plan.PlanResponse;
import com.mymatch.repository.PlanRepository;
import com.mymatch.mapper.PlanMapper;
import com.mymatch.entity.Plan;
import com.mymatch.service.PlanService;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PlanServiceImpl implements PlanService {

    PlanRepository planRepository;
    PlanMapper planMapper;

    @Override
    @Transactional
    public PlanResponse createPlan(PlanCreationRequest request) {
        if (planRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PLAN_NAME_ALREADY_EXISTS);
        }
        Plan plan = planMapper.toEntity(request);
        plan = planRepository.save(plan);
        return planMapper.toResponse(plan);
    }

    @Override
    @Transactional
    public PlanResponse updatePlan(Long id, PlanUpdateRequest request) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PLAN_NOT_FOUND));

        if (request.getName() != null
                && planRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new AppException(ErrorCode.PLAN_NAME_ALREADY_EXISTS);
        }

        planMapper.updateEntity(plan, request);
        plan = planRepository.save(plan);
        return planMapper.toResponse(plan);
    }

    @Override
    @Transactional
    public void deletePlan(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PLAN_NOT_FOUND));
        planRepository.delete(plan); // soft delete via @SQLDelete
        log.info("Deleted plan id={}", id);
    }

    @Override
    public List<PlanResponse> getAll() {
        return planRepository.findAll()
                .stream()
                .map(planMapper::toResponse)
                .toList();
    }
}