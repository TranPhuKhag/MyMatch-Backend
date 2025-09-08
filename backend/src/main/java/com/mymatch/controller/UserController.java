package com.mymatch.controller;

import com.mymatch.dto.request.user.UserCreationRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.user.UserResponse;
import com.mymatch.enums.RoleType;
import com.mymatch.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;
    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@RequestBody UserCreationRequest request) {
        var result = userService.createUser(request, RoleType.STUDENT);
        return ApiResponse.<UserResponse>builder().result(result).build();
    }
    @PostMapping("")
    @PreAuthorize("hasAuthority('user:create')")
    public ApiResponse<UserResponse> createUser(@RequestBody UserCreationRequest request) {
        var result = userService.createUser(request, request.getRoleType());
        return ApiResponse.<UserResponse>builder().result(result).build();
    }
    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo() {
        var result = userService.getMyInfo();
        return ApiResponse.<UserResponse>builder().result(result).build();
    }
}
