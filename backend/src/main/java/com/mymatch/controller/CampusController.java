package com.mymatch.controller;

import com.mymatch.dto.request.campus.CampusCreationRequest;
import com.mymatch.dto.request.campus.CampusUpdateRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.campus.CampusResponse;
import com.mymatch.service.CampusService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campuses")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CampusController {

    CampusService campusService;

    @PostMapping
    public ApiResponse<CampusResponse> createCampus(@RequestBody CampusCreationRequest req) {
        return ApiResponse.<CampusResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tạo cơ sở thành công")
                .result(campusService.createCampus(req))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CampusResponse> getById(@PathVariable Long id) {
        return ApiResponse.<CampusResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin cơ sở thành công")
                .result(campusService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<CampusResponse> updateCampus(@PathVariable Long id, @RequestBody CampusUpdateRequest req) {
        return ApiResponse.<CampusResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật cơ sở thành công")
                .result(campusService.updateCampus(id, req))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<CampusResponse> deleteCampus(@PathVariable Long id) {
        return ApiResponse.<CampusResponse>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Xoá cơ sở thành công")
                .result(campusService.deleteCampus(id))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<CampusResponse>> getAllCampuses(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(required = false) Long universityId) {
        return ApiResponse.<PageResponse<CampusResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách cơ sở thành công")
                .result(campusService.getAllCampuses(page, size, sort, universityId))
                .build();
    }
}
