package com.mymatch.service;

import com.mymatch.dto.response.filemanager.FileDownloadResponse;
import com.mymatch.enums.StorageType;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileManagerService {
    /**
     * Lưu một file vào một thư mục con.
     *
     * @param file         File được upload.
     * @param subDirectory Thư mục con để lưu (ví dụ: "avatars", "products").
     * @param type         Loại lưu trữ (PUBLIC hoặc PRIVATE).
     * @return Tên file duy nhất đã được tạo.
     */
    String save(MultipartFile file, String subDirectory, StorageType type);

    /**
     * Lưu file bằng streaming InputStream.
     *
     * @param inputStream      Dữ liệu file stream.
     * @param originalFilename Tên gốc của file.
     * @param contentType      MIME type.
     * @param subDirectory     Thư mục con để lưu (ví dụ: "avatars", "products").
     * @param type             Loại lưu trữ (PUBLIC hoặc PRIVATE).
     * @return Đường dẫn hoặc URL của file đã lưu.
     */
    String saveStream(InputStream inputStream,
                      String originalFilename,
                      String contentType,
                      String subDirectory,
                      StorageType type);


    /**
     * Xóa một file khỏi thư mục con.
     *
     * @param filename     Tên file cần xóa.
     * @param subDirectory Thư mục con chứa file.
     * @param type         Loại lưu trữ của file cần xóa.
     */
    void delete(String filename, String subDirectory, StorageType type);

    String buildFilePath(Long userId, String uuid);




}
