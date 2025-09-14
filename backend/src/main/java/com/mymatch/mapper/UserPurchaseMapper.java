package com.mymatch.mapper;

import com.mymatch.dto.request.purchase.UserPurchaseCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.mymatch.dto.response.purchase.UserPurchaseResponse;
import com.mymatch.entity.UserPurchase;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface UserPurchaseMapper {
    UserPurchase toEntity(UserPurchaseCreateRequest request);

    @Mapping(target = "user.student", ignore = true)
    @Mapping(target = "user.role", ignore = true)
//    @Mapping(target = "plan", source = "plan.name")
    UserPurchaseResponse toResponse(UserPurchase entity);

}