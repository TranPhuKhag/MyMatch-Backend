package com.mymatch.service;

import com.mymatch.dto.request.product.ProductCreationRequest;
import com.mymatch.dto.request.product.ProductUpdateRequest;
import com.mymatch.dto.response.product.ProductResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface ProductService {
    @PreAuthorize("hasAuthority('product:create')")
    ProductResponse createProduct(ProductCreationRequest request);
    @PreAuthorize("hasAuthority('product:update')")
    ProductResponse updateProduct(Long id, ProductUpdateRequest request);
    @PreAuthorize("hasAuthority('product:delete')")
    void deleteProduct(Long id);
    List<ProductResponse> getAllProducts();
}
