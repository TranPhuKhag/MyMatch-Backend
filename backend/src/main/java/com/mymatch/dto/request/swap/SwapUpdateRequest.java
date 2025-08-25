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
public class SwapUpdateRequest {
    String targetClass;
    Long lecturerFromId;
    Long lecturerToId;
    ClassesSlot slotFrom;
    ClassesSlot slotTo;
    String reason;
    Visibility visibility;
}
