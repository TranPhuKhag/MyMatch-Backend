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
public class ReviewCriteriaCreateRequest {

    @NotBlank
    private String name;

    @NotNull
    private CriteriaType type;

    private String description;
}
