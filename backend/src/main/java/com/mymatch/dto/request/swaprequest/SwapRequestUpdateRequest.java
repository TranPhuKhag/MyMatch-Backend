package com.mymatch.dto.request.swaprequest;

import com.mymatch.enums.ClassesSlot;
import com.mymatch.enums.SwapRequestStatus;
import com.mymatch.enums.Visibility;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SwapRequestUpdateRequest {
    Long courseId;
    String fromClass;
    String targetClass;
    Long lecturerFromId;
    Long lecturerToId;
    List<Long> preferredDayIds;
    ClassesSlot slot;
    String reason;
    Visibility visibility;
    Long lecturerId;
    SwapRequestStatus status;
}
