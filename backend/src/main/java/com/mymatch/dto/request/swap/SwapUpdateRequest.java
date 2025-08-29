package com.mymatch.dto.request.swap;

import com.mymatch.enums.SwapDecision;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SwapUpdateRequest {
    @NotNull
    SwapDecision decision;
    String reason;
}
