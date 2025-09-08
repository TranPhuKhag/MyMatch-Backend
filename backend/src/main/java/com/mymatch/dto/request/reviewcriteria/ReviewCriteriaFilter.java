package com.mymatch.dto.request.reviewcriteria;

import com.mymatch.enums.CriteriaType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCriteriaFilter {
    private String name;
    private CriteriaType type;

}
