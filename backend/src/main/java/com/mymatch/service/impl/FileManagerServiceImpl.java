package com.mymatch.service.impl;

import com.mymatch.enums.StorageType;
import com.mymatch.service.FileManagerService;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileManagerServiceImpl implements FileManagerService {
    @Value("${file.storage.private}")
    String privatePath;

    @Value("${file.storage.public}")
    String publicPath;


    Path privateRoot;
    Path publicRoot;


    @PostConstruct
    void init() {
        this.privateRoot = Paths.get(privatePath).normalize();
        this.publicRoot = Paths.get(publicPath).normalize();

        try {
            Files.createDirectories(privateRoot);
            Files.createDirectories(publicRoot);
        } catch (IOException e) {
            throw new RuntimeException("Could not create storage directories!", e);
        }
    }

    @Override
    public String save(MultipartFile file, String subDirectory, StorageType type) {
        try {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isBlank()) {
                throw new RuntimeException("File name is invalid");
            }

            Path targetDir = resolveRoot(type).resolve(subDirectory).normalize();
            Files.createDirectories(targetDir);

            Path targetFile = targetDir.resolve(originalFilename).normalize();
            file.transferTo(targetFile);

            log.info("File saved: {}", targetFile);
            if (type == StorageType.PUBLIC) {
                return "/public/" + subDirectory + "/" + originalFilename;
            } else {
                return "/private/" + subDirectory + "/" + originalFilename;
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public void delete(String filename, String subDirectory, StorageType type) {
        try {
            Path filePath = resolveRoot(type).resolve(subDirectory).resolve(filename).normalize();
            Files.deleteIfExists(filePath);
            log.info("File deleted: {}", filePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete file " + filename, e);
        }
    }

    @Override
    public String buildFilePath(Long userId, String prefix) {

        return userId.toString() + "/" + prefix;
    }


    private Path resolveRoot(StorageType type) {
        return type == StorageType.PUBLIC ? publicRoot : privateRoot;
    }
}
