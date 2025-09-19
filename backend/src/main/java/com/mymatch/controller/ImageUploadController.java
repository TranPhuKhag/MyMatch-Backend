package com.mymatch.controller;

import com.mymatch.dto.response.ApiResponse;
import com.mymatch.service.UploadImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageUploadController {
    UploadImageService uploadImageService;

    @PostMapping()
    public ApiResponse<String> uploadEvidenceFile(@RequestParam("file") MultipartFile file) {
        String fileUrl = uploadImageService.uploadImage(file);
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Tải lên file thành công")
                .result(fileUrl)
                .build();
    }
}
