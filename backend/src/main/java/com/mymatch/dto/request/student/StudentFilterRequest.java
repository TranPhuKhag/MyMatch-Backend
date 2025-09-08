package com.mymatch.dto.request.student;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class StudentFilterRequest {
    @Builder.Default int page = 1;
    @Builder.Default int size = 10;
    @Builder.Default String sortBy = "id";

    Long campusId;
}