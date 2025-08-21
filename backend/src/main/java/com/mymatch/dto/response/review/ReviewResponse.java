package com.mymatch.dto.response.review;

import com.mymatch.dto.response.course.CourseResponse;
import com.mymatch.dto.response.reviewdetail.ReviewDetailResponse;
import com.mymatch.dto.response.semester.SemesterResponse;
import com.mymatch.dto.response.student.StudentResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewResponse {
    Long id;
    StudentResponse student;
    CourseResponse course;
    SemesterResponse semester;
    Double overallScore;
    Boolean isVerified;
    Boolean isAnonymous;
    String evidenceUrl;
    List<ReviewDetailResponse> details;
}
