package com.mymatch.dto.request.lecturer;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LecturerFilterRequest {
    Long campusId;
    String name;   // search contain
    String code;   // search contain
}
