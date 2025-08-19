package com.mymatch.dto.response.student;

import com.mymatch.dto.response.campus.CampusResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentResponse {
    Long id;
    String studentCode;
    Long campusId;
    String campusName;
    Long universityId;
    String universityName;

    String skill;
    Double goals;
    String description;
}