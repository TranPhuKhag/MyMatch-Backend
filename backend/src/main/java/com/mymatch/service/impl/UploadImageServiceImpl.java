package com.mymatch.service.impl;

import com.mymatch.enums.StorageType;
import com.mymatch.service.FileManagerService;
import com.mymatch.service.UploadImageService;
import com.mymatch.utils.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UploadImageServiceImpl implements UploadImageService {
    FileManagerService fileManagerService;

    @Override
    public String uploadImage(MultipartFile file) {
        Long currentUserId = SecurityUtil.getCurrentUserId();
        String uuid = java.util.UUID.randomUUID().toString();
        return fileManagerService.save(file, fileManagerService.buildFilePath(currentUserId, uuid), StorageType.PUBLIC);
    }
}
