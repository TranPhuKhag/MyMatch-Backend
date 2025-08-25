package com.mymatch.dto.response.swap;

import com.mymatch.dto.response.course.CourseResponse;
import com.mymatch.enums.SwapStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SwapResponse {
    Long id;

    Long requestFromId;
    Long requestToId;

    Long studentFromId;
    String studentFromName;

    Long studentToId;
    String studentToName;

    SwapStatus status;
    String reason;
    LocalDateTime approvedAt;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    CourseResponse course;
}
