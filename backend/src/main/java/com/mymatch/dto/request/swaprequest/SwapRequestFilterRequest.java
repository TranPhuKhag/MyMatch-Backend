package com.mymatch.dto.request.swaprequest;

import com.mymatch.enums.ClassesSlot;
import com.mymatch.enums.Visibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.DayOfWeek;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class SwapRequestFilterRequest  {
    @Builder.Default int page = 1;
    @Builder.Default int size = 10;
    @Builder.Default String sortBy = "id"; // asc mặc định, giống StudentServiceImpl

    Long studentId;
    Long courseId;

    Long lecturerFromId;
    Long lecturerToId;

    ClassesSlot slotFrom;
    ClassesSlot slotTo;

    Visibility visibility;

    String fromClass;   // nếu muốn lọc chính xác theo mã lớp nguồn
    String targetClass; // nếu muốn lọc chính xác theo mã lớp đích
    Set<DayOfWeek> fromDays;
    Set<DayOfWeek> toDays;
}
