package com.mymatch.controller;

import com.mymatch.dto.request.role.PermissionRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.role.PermissionResponse;
import com.mymatch.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {

    PermissionService permissionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest req) {
        return ApiResponse.<PermissionResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tạo quyền thành công")
                .result(permissionService.createPermission(req))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<PermissionResponse> getById(@PathVariable Long id) {
        return ApiResponse.<PermissionResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin quyền thành công")
                .result(permissionService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<PermissionResponse> updatePermission(@PathVariable Long id, @RequestBody PermissionRequest req) {
        return ApiResponse.<PermissionResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật quyền thành công")
                .result(permissionService.updatePermission(id, req))
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<PermissionResponse> deletePermission(@PathVariable Long id) {
        return ApiResponse.<PermissionResponse>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Xoá quyền thành công")
                .result(permissionService.deletePermission(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> getAllPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách quyền thành công")
                .result(permissionService.getAllPermissions())
                .build();
    }
}
