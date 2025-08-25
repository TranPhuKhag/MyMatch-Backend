package com.mymatch.dto.response.swaprequest;

import com.mymatch.dto.response.course.CourseResponse;
import com.mymatch.dto.response.lecturer.LecturerResponse;
import com.mymatch.dto.response.student.StudentResponse;
import com.mymatch.enums.ClassesSlot;
import com.mymatch.enums.SwapRequestStatus;
import com.mymatch.enums.Visibility;
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
    CourseResponse course;

    String fromClass;
    String targetClass;
    ClassesSlot slotFrom;
    ClassesSlot slotTo;
    String reason;
    Visibility visibility;
    LocalDateTime expiresAt;
    LocalDateTime createAt;
    LocalDateTime updateAt;
    LecturerResponse lecturerFrom;
    LecturerResponse lecturerTo;
    SwapRequestStatus status;
}
