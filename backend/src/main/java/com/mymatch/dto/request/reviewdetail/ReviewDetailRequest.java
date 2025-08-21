package com.mymatch.dto.request.reviewdetail;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewDetailRequest {
    @NotNull
    Long criteriaId;
    int score;
    String comment;
    Boolean isYes;
}
