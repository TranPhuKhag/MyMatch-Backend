package com.mymatch.dto.request.material;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialUpdateRequest {
    String name;
    String description;
    Long courseId;
    Long lecturerId;
    @Schema(format = "binary")
    MultipartFile file;
}
