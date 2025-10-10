package com.mymatch.controller;

import com.mymatch.dto.request.plan.PlanCreationRequest;
import com.mymatch.dto.request.plan.PlanUpdateRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.plan.PlanResponse;
import com.mymatch.service.PlanService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlanController {

    PlanService planService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PlanResponse> createPlan(@Valid @RequestBody PlanCreationRequest request) {
        return ApiResponse.<PlanResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tạo gói thành công")
                .result(planService.createPlan(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<PlanResponse> updatePlan(@PathVariable Long id,
                                            @Valid @RequestBody PlanUpdateRequest request) {
        return ApiResponse.<PlanResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật gói thành công")
                .result(planService.updatePlan(id, request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<PlanResponse>> getAll() {
        return ApiResponse.<List<PlanResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách gói thành công")
                .result(planService.getAll())
                .build();
    }


    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Xoá gói thành công")
                .build();
    }
}
