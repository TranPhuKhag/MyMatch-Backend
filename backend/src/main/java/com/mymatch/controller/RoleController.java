package com.mymatch.controller;

import com.mymatch.dto.request.role.RoleCreationRequest;
import com.mymatch.dto.request.role.RoleUpdateRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.role.RoleResponse;
import com.mymatch.service.RoleService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleService roleService;

    @GetMapping("/{id}")
    public ApiResponse<RoleResponse> getById(@PathVariable Long id) {
        return ApiResponse.<RoleResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin vai trò thành công")
                .result(roleService.getById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<RoleResponse> updateRole(@PathVariable Long id, @RequestBody RoleUpdateRequest req) {
        return ApiResponse.<RoleResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật vai trò thành công")
                .result(roleService.updateRole(id, req))
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<RoleResponse> deleteRole(@PathVariable Long id) {
        return ApiResponse.<RoleResponse>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Xoá vai trò thành công")
                .result(roleService.deleteRole(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAllRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách vai trò thành công")
                .result(roleService.getAllRoles())
                .build();
    }
}
