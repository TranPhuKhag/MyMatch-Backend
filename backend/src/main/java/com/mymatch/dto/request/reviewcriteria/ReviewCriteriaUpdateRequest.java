package com.mymatch.dto.request.reviewcriteria;

import com.mymatch.enums.CriteriaType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCriteriaUpdateRequest {
    @NotBlank(message = "Mô tả không được để trống")
    private String description;
}
