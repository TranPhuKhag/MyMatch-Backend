package com.mymatch.dto.request.student;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentUpdateRequest {
    String studentCode;
    Long campusId;
    String skill;
    Double goals;
    String description;
}
