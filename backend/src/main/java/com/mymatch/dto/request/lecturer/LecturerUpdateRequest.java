package com.mymatch.dto.request.lecturer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LecturerUpdateRequest {
    @NotBlank
    String name;

    String code;

    String bio;

    @NotNull
    Long campusId;

    List<Long> tagIds;

}
