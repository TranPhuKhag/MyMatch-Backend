package com.mymatch.controller;

import com.mymatch.dto.request.material.MaterialCreationRequest;
import com.mymatch.dto.request.material.MaterialFilter;
import com.mymatch.dto.request.material.MaterialUpdateRequest;
import com.mymatch.dto.response.ApiResponse;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.filemanager.FileDownloadResponse;
import com.mymatch.dto.response.material.MaterialResponse;
import com.mymatch.service.MaterialService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/materials")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MaterialController {

    MaterialService materialService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<MaterialResponse> createMaterial(
            @ModelAttribute MaterialCreationRequest request) throws Exception {
        return ApiResponse.<MaterialResponse>builder()
                .result(materialService.createMaterial(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<MaterialResponse> getMaterialById(@PathVariable Long id) {
        return ApiResponse.<MaterialResponse>builder()
                .result(materialService.getMaterialById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<MaterialResponse> updateMaterial(
            @PathVariable Long id,
            @RequestBody MaterialUpdateRequest request) {
        return ApiResponse.<MaterialResponse>builder()
                .result(materialService.updateMaterial(id, request))
                .build();
    }

    @PostMapping("/{id}/purchase")
    public ApiResponse<MaterialResponse> purchaseMaterial(@PathVariable Long id) {
        return ApiResponse.<MaterialResponse>builder()
                .result(materialService.purchaseMaterial(id))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<MaterialResponse>> getAllMaterials(
            MaterialFilter filter,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {
        return ApiResponse.<PageResponse<MaterialResponse>>builder()
                .result(materialService.getAllMaterials(filter, page, size, sortBy, sortDir))
                .build();
    }

    @GetMapping("/{materialId}/download")
    public ResponseEntity<Void> downloadMaterial(@PathVariable Long materialId) {
        FileDownloadResponse downloadInfo = materialService.downloadMaterial(materialId);

        String encodedFileName = URLEncoder.encode(downloadInfo.getFileName(), StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"")
                .header("Content-Type", downloadInfo.getContentType())
                .header("X-Accel-Redirect", downloadInfo.getNginxPath())
                .build();

    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ApiResponse.<Void>builder()
                .message("Material deleted successfully")
                .build();
    }
}