package com.mymatch.dto.response.lecturercourse;

import com.mymatch.dto.response.course.CourseResponse;
import com.mymatch.dto.response.lecturer.LecturerResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LecturerCourseResponse {
    CourseResponse course;
    LecturerResponse lecturer;
}
