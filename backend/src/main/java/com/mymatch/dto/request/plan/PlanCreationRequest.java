package com.mymatch.dto.request.plan;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlanCreationRequest {

    @NotBlank(message = "Tên gói không được để trống")
    @Size(max = 255, message = "Tên gói tối đa 255 ký tự")
    String name;

    String description;

    @NotNull(message = "Giá gói (coin) không được null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá gói (coin) phải > 0")
    Double coin;

    String imageUrl;

    @Min(value = 1, message = "Thời hạn tối thiểu 1 ngày")
    int durationDays;
}
