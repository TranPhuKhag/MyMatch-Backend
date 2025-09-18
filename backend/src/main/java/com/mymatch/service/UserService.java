package com.mymatch.service;

import com.mymatch.dto.request.user.UserCreationRequest;
import com.mymatch.dto.request.user.UserUpdateRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.user.UserResponse;
import com.mymatch.enums.RoleType;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserService {
    public UserResponse createUser(UserCreationRequest request, RoleType roleType);
    UserResponse updateUser(UserUpdateRequest request, Long id);
    public UserResponse deleteUser(Long id);
    public void unBanUser(Long userId);
    @PreAuthorize("hasAnyAuthority('user:read')")
    public UserResponse getUser(Long id);
    public UserResponse getMyInfo();
    @PreAuthorize("hasAnyAuthority('user:read')")
    public PageResponse<UserResponse> getAllUsers(int page, int size, String sort, String filter, String searchTerm);
}
