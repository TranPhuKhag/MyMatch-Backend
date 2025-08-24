package com.mymatch.mapper;

import com.mymatch.dto.request.role.PermissionRequest;
import com.mymatch.dto.response.role.PermissionResponse;
import com.mymatch.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PermissionMapper {
    @Mapping(target = "id", ignore = true)
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

    @Mapping(target = "id", ignore = true)
    void updatePermission(@MappingTarget Permission permission, PermissionRequest request);
}
