package com.mymatch.utils;

import java.text.Normalizer;

public class FileNameUtil {

    /**
     * Làm sạch tên file để lưu trữ:
     * - Loại bỏ dấu tiếng Việt, ký tự đặc biệt
     * - Thay space thành "_"
     * - Giữ nguyên đuôi file (.png, .jpg, ...)
     */
    public static String sanitizeFileNameForStorage(String originalFileName) {
        if (originalFileName == null || originalFileName.isEmpty()) {
            return "file";
        }

        // Tách phần extension
        int dotIndex = originalFileName.lastIndexOf(".");
        String namePart = (dotIndex > 0) ? originalFileName.substring(0, dotIndex) : originalFileName;
        String extPart = (dotIndex > 0) ? originalFileName.substring(dotIndex) : "";

        // Bỏ dấu
        String normalized = Normalizer.normalize(namePart, Normalizer.Form.NFD);
        String noAccent = normalized.replaceAll("\\p{M}", "");

        // Thay space thành "_", bỏ ký tự không hợp lệ
        String safeName = noAccent.replaceAll("[^a-zA-Z0-9-_ ]", "")
                                  .replaceAll(" ", "_");

        // Nếu sau khi clean bị rỗng thì set default
        if (safeName.isEmpty()) {
            safeName = "file";
        }

        return safeName + extPart;
    }
}

