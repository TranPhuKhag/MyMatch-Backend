package com.mymatch.mapper;

import com.mymatch.dto.request.user.UserCreationRequest;
import com.mymatch.dto.response.user.UserResponse;
import com.mymatch.entity.Role;
import com.mymatch.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "password", source = "hashedPassword")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "id", ignore = true)
    User toUser(UserCreationRequest request, Role role, String hashedPassword);

    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "role", source = "role.name")
    @Mapping(target = "deleted", source = "deleted")
    UserResponse toUserResponse(User user);
}
