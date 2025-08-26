package com.mymatch.dto.request.swaprequest;

import com.mymatch.enums.ClassesSlot;
import com.mymatch.enums.SwapRequestStatus;
import com.mymatch.enums.Visibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SwapRequestCreationRequest {
    @NotNull
    Long courseId;
    @NotBlank
    String fromClass;
    @NotBlank
    String targetClass;
    @NotNull
    Long lecturerFromId;
    @NotNull
    Long lecturerToId;

    @Builder.Default
    ClassesSlot slotFrom = ClassesSlot.SLOT_1; // optional, default
    @Builder.Default
    ClassesSlot slotTo   = ClassesSlot.SLOT_1; // optional, default

    String reason;
    @Builder.Default
    SwapRequestStatus status = SwapRequestStatus.SENT; // optional, default SENT

    @Builder.Default
    Visibility visibility = Visibility.PUBLIC;
    LocalDateTime expiresAt = LocalDateTime.now().plusDays(14); // optional, default 7 days from now

}
