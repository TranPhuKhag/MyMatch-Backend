package com.mymatch.dto.response.filemanager;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileDownloadResponse {
    String nginxPath;     // /protected-files/user1/abc.pdf
    String fileName;      // abc.pdf
    String contentType;
}
