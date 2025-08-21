package com.mymatch.mapper;

import com.mymatch.dto.request.user.UserCreationRequest;
import com.mymatch.dto.response.auth.OutboundUserResponse;
import com.mymatch.dto.response.user.UserResponse;
import com.mymatch.entity.Role;
import com.mymatch.entity.Student;
import com.mymatch.entity.User;
import com.mymatch.entity.Wallet;
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

    @Mapping(target = "id", ignore = true) // KHÔNG đổi id
    @Mapping(target = "username", source = "userInfo.name") // KHÔNG đổi username
    @Mapping(target = "email",     source = "userInfo.email")
    @Mapping(target = "firstName", source = "userInfo.givenName")
    @Mapping(target = "lastName",  source = "userInfo.familyName")
    @Mapping(target = "avatarUrl", source = "userInfo.picture")
    @Mapping(target = "address",   source = "userInfo.locale")
    @Mapping(target = "role",      ignore = true)
    @Mapping(target = "wallet",    ignore = true)
    @Mapping(target = "student",   ignore = true)
    @Mapping(target = "password",  ignore = true)
    User toUserFromGoogle(OutboundUserResponse userInfo, Role role, Wallet wallet, Student student);
}
