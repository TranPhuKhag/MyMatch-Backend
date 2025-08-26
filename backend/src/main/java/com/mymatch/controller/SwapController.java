package com.mymatch.controller;


import com.mymatch.dto.request.swap.SwapFilterRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.swap.SwapResponse;
import com.mymatch.service.SwapService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/swaps")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SwapController {
    SwapService swapService;

    @GetMapping
    public ApiResponse<PageResponse<SwapResponse>> getAll(@ModelAttribute SwapFilterRequest req) {
        return ApiResponse.<PageResponse<SwapResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách swap thành công")
                .result(swapService.getAll(req))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<SwapResponse> getById(@PathVariable Long id) {
        return ApiResponse.<SwapResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy chi tiết swap thành công")
                .result(swapService.getById(id))
                .build();
    }
}
