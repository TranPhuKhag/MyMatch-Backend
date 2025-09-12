package com.mymatch.mapper;

import com.mymatch.dto.request.product.ProductCreationRequest;
import com.mymatch.dto.request.product.ProductUpdateRequest;
import com.mymatch.dto.response.product.ProductResponse;
import com.mymatch.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    Product toEntity(ProductCreationRequest req);
    ProductResponse toResponse(Product entity);
    void updateEntity(@MappingTarget Product entity, ProductUpdateRequest req);
}
