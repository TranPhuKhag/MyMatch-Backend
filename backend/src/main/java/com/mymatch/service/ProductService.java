package com.mymatch.service;

import com.mymatch.dto.request.product.ProductCreationRequest;
import com.mymatch.dto.request.product.ProductUpdateRequest;
import com.mymatch.dto.response.product.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductCreationRequest request);
    ProductResponse updateProduct(Long id, ProductUpdateRequest request);
    void deleteProduct(Long id);
    List<ProductResponse> getAllProducts();
}
