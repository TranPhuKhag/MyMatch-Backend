package com.mymatch.dto.request.campus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CampusCreationRequest {
    @NotBlank(message = "Tên campus không được để trống")
    @Size(max = 255, message = "Tên campus không được vượt quá 255 ký tự")
    String name;

    @NotBlank(message = "Address không được để trống")
    String address;


    String imgUrl;

    @NotNull(message = "ID đại học không được để trống")
    Long universityId;
}
