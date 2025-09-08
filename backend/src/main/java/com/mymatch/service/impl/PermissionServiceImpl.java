package com.mymatch.service.impl;

import com.mymatch.dto.request.role.PermissionRequest;
import com.mymatch.dto.response.role.PermissionResponse;
import com.mymatch.entity.Permission;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.PermissionMapper;
import com.mymatch.repository.PermissionRepository;
import com.mymatch.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImpl implements PermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse createPermission(PermissionRequest request) {
        if (permissionRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        Permission permission = permissionMapper.toPermission(request);
        Permission saved = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(saved);
    }

    @Override
    public PermissionResponse updatePermission(Long id, PermissionRequest request) {
        Permission existing = permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        if (request.getName() != null && permissionRepository.existsByName(request.getName())
                && !existing.getName().equals(request.getName())) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        permissionMapper.updatePermission(existing, request);
        Permission updated = permissionRepository.save(existing);
        return permissionMapper.toPermissionResponse(updated);
    }

    @Override
    public PermissionResponse deletePermission(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        PermissionResponse response = permissionMapper.toPermissionResponse(permission);
        permissionRepository.delete(permission);
        return response;
    }

    @Override
    public PermissionResponse getById(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }
}
