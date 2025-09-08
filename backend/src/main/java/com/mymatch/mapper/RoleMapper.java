package com.mymatch.mapper;

import com.mymatch.dto.request.role.RoleCreationRequest;
import com.mymatch.dto.request.role.RoleUpdateRequest;
import com.mymatch.dto.response.role.RoleResponse;
import com.mymatch.entity.Permission;
import com.mymatch.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Set;

@Mapper(
        componentModel = "spring",
        uses = {PermissionMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "permissions", source = "permissions")
    Role toRole(RoleCreationRequest request, Set<Permission> permissions);

    RoleResponse toRoleResponse(Role role);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "permissions", source = "permissions")
    void updateRole(@MappingTarget Role role, RoleUpdateRequest request, Set<Permission> permissions);
}
