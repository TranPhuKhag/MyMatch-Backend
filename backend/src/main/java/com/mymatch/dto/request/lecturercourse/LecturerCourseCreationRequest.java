package com.mymatch.dto.request.lecturercourse;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LecturerCourseCreationRequest {
    @NotNull
    Long courseId;
    @NotNull
    Long lecturerId;
}
