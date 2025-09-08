package com.mymatch.dto.request.campus;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CampusUpdateRequest {
    @Size(max = 255, message = "Tên campus không được vượt quá 255 ký tự")
    String name;

    String address;

    String description;

    String imgUrl;

    Long universityId;
}