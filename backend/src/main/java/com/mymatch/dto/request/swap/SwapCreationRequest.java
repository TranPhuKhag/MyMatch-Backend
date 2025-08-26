package com.mymatch.dto.request.swap;

import com.mymatch.enums.ClassesSlot;
import com.mymatch.enums.SwapMode;
import com.mymatch.enums.Visibility;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SwapCreationRequest {
    @NotNull
    Long requestFromId; // request của A
    @NotNull
    Long requestToId;   // request của B

    @Builder.Default
    SwapMode mode = SwapMode.MANUAL; // MANUAL (Luồng 2) hoặc AUTOMATIC (Luồng 1)
    String reason;
}
