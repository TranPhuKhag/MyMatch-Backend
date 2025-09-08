package com.mymatch.dto.request.review;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewUpdateRequest {
    Boolean isVerified;   // only for admin
    String evidenceUrl;

}
