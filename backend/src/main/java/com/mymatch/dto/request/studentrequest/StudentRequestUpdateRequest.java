package com.mymatch.dto.request.studentrequest;
import com.mymatch.enums.RequestStatus;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class StudentRequestUpdateRequest {
    @NotBlank String requestDetail;
    @PositiveOrZero Double goal;
    @Size(max = 8) String classCode;
    @Size(max = 1000) String description;

    @NotNull Long courseId;
    @NotNull Long semesterId;
    @NotNull Long campusId;
    Set<Long> skillIds;
    RequestStatus status;

}