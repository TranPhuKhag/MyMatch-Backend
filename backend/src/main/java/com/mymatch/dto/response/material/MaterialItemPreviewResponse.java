package com.mymatch.dto.response.material;


import com.mymatch.entity.Material;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialItemPreviewResponse {
    Long id;
    String fileURL;
    Double size;
    String originalFileName;
    String fileType;
}
