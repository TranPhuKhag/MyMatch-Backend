package com.mymatch.dto.response.course;

import com.mymatch.dto.response.university.UniversityResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseResponse {
    Long id;
    String code;
    String name;
}
