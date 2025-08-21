package com.mymatch.dto.request.review;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewFilterRequest {
    Long lecturerId;
    Long courseId;
    Long studentId;
    Long semesterId;
    Boolean isVerified;
    Boolean isAnonymous;
}
