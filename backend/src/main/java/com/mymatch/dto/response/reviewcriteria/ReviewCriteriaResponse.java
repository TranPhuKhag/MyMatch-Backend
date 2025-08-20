package com.mymatch.dto.response.reviewcriteria;

import com.mymatch.enums.CriteriaType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCriteriaResponse {

    Long id;

    String name;

    CriteriaType type;

    String description;
}
