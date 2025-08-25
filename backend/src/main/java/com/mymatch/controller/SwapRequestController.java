package com.mymatch.controller;

import com.mymatch.dto.request.swaprequest.SwapRequestCreationRequest;
import com.mymatch.dto.request.swaprequest.SwapRequestFilterRequest;
import com.mymatch.dto.request.swaprequest.SwapRequestUpdateRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.swaprequest.SwapRequestResponse;
import com.mymatch.service.SwapRequestService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/swap-requests")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SwapRequestController {
    SwapRequestService swapRequestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<SwapRequestResponse> createSwapRequest(@RequestBody @Valid SwapRequestCreationRequest req) {
        return ApiResponse.<SwapRequestResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tạo yêu cầu đổi lớp thành công")
                .result(swapRequestService.createSwapRequest(req))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<SwapRequestResponse> getById(@PathVariable Long id) {
        return ApiResponse.<SwapRequestResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy yêu cầu đổi lớp thành công")
                .result(swapRequestService.getSwapRequestById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<SwapRequestResponse> updateSwapRequest(
            @PathVariable Long id,
            @RequestBody @Valid SwapRequestUpdateRequest req
    ) {
        return ApiResponse.<SwapRequestResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật yêu cầu đổi lớp thành công")
                .result(swapRequestService.updateSwapRequest(id, req))
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteSwapRequest(@PathVariable Long id) {
        swapRequestService.deleteSwapRequest(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Xoá yêu cầu đổi lớp thành công")
                .build();
    }
    @GetMapping
    public ApiResponse<PageResponse<SwapRequestResponse>> getAllSwapRequests(
            @ModelAttribute SwapRequestFilterRequest req) {
        return ApiResponse.<PageResponse<SwapRequestResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách yêu cầu đổi lớp thành công")
                .result(swapRequestService.getAllSwapRequests(req))
                .build();
    }
    @PatchMapping("/{id}/cancel")
    public ApiResponse<SwapRequestResponse> cancel(@PathVariable Long id) {
        return ApiResponse.<SwapRequestResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Hủy yêu cầu đổi lớp thành công")
                .result(swapRequestService.cancelSwapRequest(id))
                .build();
    }
}
