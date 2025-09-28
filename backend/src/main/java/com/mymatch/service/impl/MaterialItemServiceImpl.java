package com.mymatch.service.impl;

import com.mymatch.dto.request.material.MaterialItemUploadRequest;
import com.mymatch.dto.response.filemanager.FileDownloadResponse;
import com.mymatch.dto.response.material.MaterialItemPreviewResponse;
import com.mymatch.entity.Material;
import com.mymatch.entity.MaterialItem;
import com.mymatch.enums.StorageType;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.MaterialItemMapper;
import com.mymatch.repository.MaterialItemRepository;
import com.mymatch.repository.MaterialPurchaseRepository;
import com.mymatch.service.FileManagerService;
import com.mymatch.service.MaterialItemService;
import com.mymatch.utils.FileNameUtil;
import com.mymatch.utils.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MaterialItemServiceImpl implements MaterialItemService {
    FileManagerService fileManagerService;
    MaterialItemRepository materialItemRepository;
    MaterialItemMapper materialItemMapper;
    MaterialPurchaseRepository materialPurchaseRepository;

    public MaterialItemPreviewResponse uploadMaterialItem(
            MaterialItemUploadRequest request,
            InputStream inputStream
    ) throws Exception {
        // Tính size bằng cách lấy từ MultipartFile (metadata), không cần đọc hết file
        Double fileSize = getFileSizeInMB(request.getFile());
        if (fileSize > 500.0) { // ví dụ limit 500MB
            throw new AppException(ErrorCode.FILE_SIZE_EXCEEDED);
        }

        Long currentUserId = SecurityUtil.getCurrentUserId();
        String uuid = java.util.UUID.randomUUID().toString();
        String safeFileName = FileNameUtil.sanitizeFileNameForStorage(request.getFile().getOriginalFilename());

        // Lưu file bằng streaming
        String fileUrl = fileManagerService.saveStream(
                inputStream,
                safeFileName,
                request.getFile().getContentType(),
                buildFilePath(currentUserId, uuid),
                StorageType.PRIVATE
        );

        MaterialItem materialItem = MaterialItem.builder()
                .originalFileName(request.getFile().getOriginalFilename())
                .fileURL(fileUrl)
                .fileType(request.getFile().getContentType())
                .size(fileSize)
                .build();

        materialItemRepository.save(materialItem);

        return materialItemMapper.toMaterialItemPreviewResponse(materialItem);
    }


    public FileDownloadResponse downloadMaterialItem(Long materialItemId) {
        MaterialItem materialItem = materialItemRepository.findById(materialItemId)
                                                          .orElseThrow(() -> new AppException(ErrorCode.MATERIAL_ITEM_NOT_FOUND));
        Long currentUserId = SecurityUtil.getCurrentUserId();
        Material material = materialItem.getMaterial();
        boolean isPurchased = false;
        if (material != null) {
            isPurchased = materialPurchaseRepository
                    .existsByMaterial_IdAndAndBuyer_Id(material.getId(), currentUserId);
            if (currentUserId.equals(material.getOwner().getId())) {
                isPurchased = true;
            }
        } else {
            isPurchased = true; // item không liên kết với material nào, có thể tải về
        }

        if (!isPurchased) {
            throw new AppException(ErrorCode.MATERIAL_NOT_PURCHASED);
        }
        return FileDownloadResponse.builder()
                .contentType(materialItem.getFileType())
                .fileName(materialItem.getOriginalFileName())
                .nginxPath(materialItem.getFileURL())
                .build();
    }

    private Double getFileSizeInMB(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new Exception("File is empty");
        }
        double sizeInMB = file.getSize() / (1024.0 * 1024.0);
        return Math.round(sizeInMB * 100.0) / 100.0; // làm tròn 2 số thập phân
    }


    private String buildFilePath(Long userId, String prefix) {

        return userId.toString() + "/" + prefix;
    }


}
