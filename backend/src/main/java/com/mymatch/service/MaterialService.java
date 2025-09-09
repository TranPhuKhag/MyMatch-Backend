package com.mymatch.service;

import com.mymatch.dto.request.material.MaterialCreationRequest;
import com.mymatch.dto.request.material.MaterialFilter;
import com.mymatch.dto.request.material.MaterialUpdateRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.filemanager.FileDownloadResponse;
import com.mymatch.dto.response.material.MaterialResponse;
import org.springframework.security.access.prepost.PreAuthorize;

public interface MaterialService {
    MaterialResponse createMaterial(MaterialCreationRequest request) throws Exception;

    MaterialResponse getMaterialById(Long id);

    MaterialResponse updateMaterial(Long id, MaterialUpdateRequest request);

    MaterialResponse purchaseMaterial(Long materialId);

    PageResponse<MaterialResponse> getAllMaterials(MaterialFilter filter, int page, int size, String sortBy, String sortDir);

    FileDownloadResponse downloadMaterial(Long materialId);

    void deleteMaterial(Long id);
}
