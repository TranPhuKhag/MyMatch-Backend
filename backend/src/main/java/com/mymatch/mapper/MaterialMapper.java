package com.mymatch.mapper;


import com.mymatch.dto.request.material.MaterialCreationRequest;
import com.mymatch.dto.request.material.MaterialUpdateRequest;
import com.mymatch.dto.response.material.MaterialResponse;
import com.mymatch.entity.Material;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = {CourseMapper.class, UserMapper.class, LecturerMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MaterialMapper {

    @Mapping(target = "items", ignore = true)
    Material toMaterial(MaterialCreationRequest request);

    void updateMaterial(@MappingTarget Material material, MaterialUpdateRequest request);


    @Mapping(target = "totalDownloads", source = "downloadCont")
    @Mapping(target = "totalPurchases", source = "purchaseCount")
    MaterialResponse toMaterialResponse(Material material);
}
