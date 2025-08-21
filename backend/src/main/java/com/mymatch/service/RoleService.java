package com.mymatch.service;

import com.mymatch.dto.request.role.RoleCreationRequest;
import com.mymatch.dto.request.role.RoleUpdateRequest;
import com.mymatch.dto.response.role.RoleResponse;
import com.mymatch.entity.Role;
import com.mymatch.enums.RoleType;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface RoleService {

    @PreAuthorize("hasAuthority('role:update')")
    RoleResponse updateRole(Long id, RoleUpdateRequest req);

    @PreAuthorize("hasAuthority('role:delete')")
    RoleResponse deleteRole(Long id);

    RoleResponse getById(Long id);

    @PreAuthorize("hasAuthority('role:read')")
    List<RoleResponse> getAllRoles();

    Role getRoleWithPermissions(RoleType roleType);

}
