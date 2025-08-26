package com.mymatch.dto.response.swaprequest;

import com.mymatch.dto.response.course.CourseResponse;
import com.mymatch.dto.response.lecturer.LecturerResponse;
import com.mymatch.dto.response.student.StudentResponse;
import com.mymatch.enums.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SwapRequestResponse {
    Long id;
    StudentResponse student;
    SwapRequestStatus status;
    CourseResponse course;
    String reason;
    LecturerResponse lecturerFrom;
    LecturerResponse lecturerTo;
    LocalDateTime createAt;
    LocalDateTime updateAt;
}
