package com.mymatch.dto.request.material;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialCreationRequest {
    @Schema(description = "Material name")
    String name;

    @Schema(description = "Material description")
    String description;

    @Schema(description = "Course ID")
    Long courseId;

    @Schema(description = "Lecturer ID")
    Long lecturerId;

    List<Long> materialItemIds;
}
