package com.mymatch.dto.request.course;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseCreationRequest {
    @NotBlank(message = "Vui lòng nhập mã môn học")
    String code;
    String name;
    Long universityId;
}
