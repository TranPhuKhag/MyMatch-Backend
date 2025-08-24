package com.mymatch.service;

import com.mymatch.dto.request.role.PermissionRequest;
import com.mymatch.dto.response.role.PermissionResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface PermissionService {

    @PreAuthorize("hasRole('MANAGER')")
    PermissionResponse createPermission(PermissionRequest request);

    @PreAuthorize("hasRole('MANAGER')")
    PermissionResponse updatePermission(Long id, PermissionRequest request);

    @PreAuthorize("hasRole('MANAGER')")
    PermissionResponse deletePermission(Long id);

    @PreAuthorize("hasRole('MANAGER')")
    PermissionResponse getById(Long id);

    @PreAuthorize("hasRole('MANAGER')")
    List<PermissionResponse> getAllPermissions();
}
