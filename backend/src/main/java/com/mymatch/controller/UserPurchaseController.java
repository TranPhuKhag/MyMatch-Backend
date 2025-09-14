package com.mymatch.controller;

import com.mymatch.dto.request.purchase.UserPurchaseCreateRequest;
import com.mymatch.dto.request.purchase.UserPurchaseFilterRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.purchase.UserPurchaseResponse;
import com.mymatch.service.UserPurchaseService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-purchases")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserPurchaseController {
    UserPurchaseService userPurchaseService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserPurchaseResponse> create(@Valid @RequestBody UserPurchaseCreateRequest request) {
        return ApiResponse.<UserPurchaseResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tạo purchase thành công")
                .result(userPurchaseService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<UserPurchaseResponse>> getAll(
            @ModelAttribute UserPurchaseFilterRequest filterRequest,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") String sortDirection
    ) {
        return ApiResponse.<PageResponse<UserPurchaseResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách purchase thành công")
                .result(userPurchaseService.getAll(filterRequest, page, size, sortBy, sortDirection))
                .build();
    }
}
