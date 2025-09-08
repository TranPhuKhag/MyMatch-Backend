package com.mymatch.dto.response.university;

import com.mymatch.dto.response.course.CourseResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UniversityResponse {
    Long id;
    String imgUrl;
    String name;
    List<CourseResponse> courses;
    LocalDateTime createAt;
    LocalDateTime updateAt;
}