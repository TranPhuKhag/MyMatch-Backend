package com.mymatch.dto.request.swaprequest;

import com.mymatch.enums.ClassesSlot;
import com.mymatch.enums.SwapRequestStatus;
import com.mymatch.enums.Visibility;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SwapRequestUpdateRequest {
    String codeCourse;
    String fromClass;
    String targetClass;
    String codeLecturerFrom;
    String codeLecturerTo;
    List<Long> preferredDayIds;
    ClassesSlot slot;
    String reason;
    Visibility visibility;
    Long lecturerId;
    SwapRequestStatus status;
    Set<DayOfWeek> fromDays;
    Set<DayOfWeek> toDays;
}
