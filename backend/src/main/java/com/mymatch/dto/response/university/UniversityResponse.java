package com.mymatch.dto.response.university;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UniversityResponse {
    Long id;
    String imgUrl;
    String name;
    LocalDateTime createAt;
    LocalDateTime updateAt;
}