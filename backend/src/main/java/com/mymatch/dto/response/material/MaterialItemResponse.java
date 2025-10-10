package com.mymatch.dto.response.material;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialItemResponse {
    Long id;
    String fileURL;
    Double size;
    String originalFileName;
    String fileType;
    int downloadCont = 0;
}
