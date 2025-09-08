package com.mymatch.dto.request.material;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialFilter {
    String name;
    String description;
    Long lecturerId;
    Long ownerId;
    Long courseId;
}
