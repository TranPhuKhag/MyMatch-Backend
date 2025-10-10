package com.mymatch.dto.request.plan;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlanUpdateRequest {

    @Size(max = 255, message = "Tên gói tối đa 255 ký tự")
    String name;

    String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Giá gói (coin) phải > 0")
    Double coin;

    String imageUrl;

    @Min(value = 1, message = "Thời hạn tối thiểu 1 ngày")
    Integer durationDays;
}