package com.mymatch.dto.request.university;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UniversityCreationRequest {
    String imgUrl;

    @NotBlank(message = "Tên trường đại học không được để trống")
    @Size(max = 255, message = "Tên trường đại học không được vượt quá 255 ký tự")
    String name;
}
