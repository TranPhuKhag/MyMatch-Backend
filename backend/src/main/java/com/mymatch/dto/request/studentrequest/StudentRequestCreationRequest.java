package com.mymatch.dto.request.studentrequest;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class StudentRequestCreationRequest {
    @NotBlank String requestDetail;

    @PositiveOrZero(message = "goal phải >= 0")
    Double goal;
    @Size(max = 8) String classCode;
    @Size(max = 1000) String description;

    @NotNull(message = "course không được null")
    Long courseId;
    @NotNull(message = "semester không được null")
    Long semesterId;
    @NotNull(message = "campus không được null")
    Long campusId;

    Set<Long> skillIds;
}