package com.mymatch.service;

import com.mymatch.dto.request.material.MaterialItemUploadRequest;
import com.mymatch.dto.response.filemanager.FileDownloadResponse;
import com.mymatch.dto.response.material.MaterialItemPreviewResponse;
import com.mymatch.entity.Material;

import java.io.InputStream;


public interface MaterialItemService {
    public MaterialItemPreviewResponse uploadMaterialItem(
            MaterialItemUploadRequest request,
            InputStream inputStream
    ) throws Exception;

    FileDownloadResponse downloadMaterialItem(Long materialItemId);
}
