package com.mymatch.controller;

import com.mymatch.dto.request.material.MaterialItemUploadRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.filemanager.FileDownloadResponse;
import com.mymatch.dto.response.material.MaterialItemPreviewResponse;
import com.mymatch.service.MaterialItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;

@RestController
@RequestMapping("/material-items")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MaterialItemController {
    MaterialItemService materialItemService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<MaterialItemPreviewResponse> uploadMaterialItem(
            @ModelAttribute MaterialItemUploadRequest request
    ) throws Exception {
        try (InputStream inputStream = request.getFile().getInputStream()) {
            MaterialItemPreviewResponse response =
                    materialItemService.uploadMaterialItem(request, inputStream);

            return ApiResponse.<MaterialItemPreviewResponse>builder()
                    .result(response)
                    .build();
        }
    }

    @GetMapping("/{materialItemId}/download")
    public ResponseEntity<Void> downloadMaterial(@PathVariable Long materialItemId) {
        FileDownloadResponse downloadInfo = materialItemService.downloadMaterialItem(materialItemId);
        String originalFileName = downloadInfo.getFileName();
        return ResponseEntity.ok()
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + originalFileName.replace("\"", "") + "\"; filename*=UTF-8''" + URLEncoder.encode(originalFileName, StandardCharsets.UTF_8))
                             .header(HttpHeaders.CONTENT_TYPE, downloadInfo.getContentType())
                             .header("X-Accel-Redirect", downloadInfo.getNginxPath())
                             .build();
    }


}
