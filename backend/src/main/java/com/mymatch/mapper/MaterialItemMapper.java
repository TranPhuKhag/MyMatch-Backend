package com.mymatch.mapper;


import com.mymatch.dto.response.material.MaterialItemPreviewResponse;
import com.mymatch.dto.response.material.MaterialItemResponse;
import com.mymatch.entity.MaterialItem;
import org.mapstruct.Mapper;

import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MaterialItemMapper {

    MaterialItemResponse toMaterialItemResponse(MaterialItem materialItem);

    MaterialItemPreviewResponse toMaterialItemPreviewResponse(MaterialItem materialItem);
}
