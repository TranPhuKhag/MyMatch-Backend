package com.mymatch.service.impl;

import com.mymatch.dto.request.role.RoleCreationRequest;
import com.mymatch.dto.request.role.RoleUpdateRequest;
import com.mymatch.exception.AppException;
import com.mymatch.dto.response.role.RoleResponse;
import com.mymatch.entity.Role;
import com.mymatch.exception.ErrorCode;
import com.mymatch.enums.RoleType;
import com.mymatch.mapper.RoleMapper;
import com.mymatch.repository.PermissionRepository;
import com.mymatch.repository.RoleRepository;
import com.mymatch.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    @Override
    public RoleResponse updateRole(Long id, RoleUpdateRequest request) {
        var existingRole = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        if (request.getName() != null
                && (roleRepository.existsByName(RoleType.valueOf(request.getName()))
                && !existingRole.getName().equals(RoleType.valueOf(request.getName())))) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }
        var permissions = new HashSet<>(permissionRepository.findAllById(request.getPermissions()));
        roleMapper.updateRole(existingRole, request, permissions);
        var updatedRole = roleRepository.save(existingRole);
        return roleMapper.toRoleResponse(updatedRole);
    }

    @Override
    public RoleResponse deleteRole(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        RoleResponse response = roleMapper.toRoleResponse(role);
        roleRepository.delete(role);
        return response;
    }

    @Override
    public RoleResponse getById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    @Override
    public Role getRoleWithPermissions(RoleType roleType) {
        return roleRepository.findByName(roleType).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
    }
}
