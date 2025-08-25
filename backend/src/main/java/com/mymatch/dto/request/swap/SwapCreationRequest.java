package com.mymatch.dto.request.swap;

import com.mymatch.enums.ClassesSlot;
import com.mymatch.enums.Visibility;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SwapCreationRequest {
    Long requestFromId;
    Long requestToId;
    Long studentFromId;   // thường = requestFrom.student.id
    Long studentToId;     // thường = requestTo.student.id
    String reason;        // optional
    Visibility visibility = Visibility.PUBLIC; // optional, default PUBLIC

}
