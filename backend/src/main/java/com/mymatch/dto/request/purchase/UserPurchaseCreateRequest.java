package com.mymatch.dto.request.purchase;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPurchaseCreateRequest{
    @NotNull(message = "planId không được null")
    Long planId;
}
