package com.mymatch.dto.request.swap;

import com.mymatch.enums.SwapDecision;
import com.mymatch.enums.SwapMode;
import com.mymatch.enums.SwapStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SwapFilterRequest {
    @Builder.Default int page = 1;
    @Builder.Default int size = 10;
    @Builder.Default String sortBy = "id";
    @Builder.Default String sortDirection = "asc";

    Long requestFromId; // lọc theo request của A
    Long requestToId;  // lọc theo request của B
    Long studentFromId;
    Long studentToId;
    Long anyStudentId; // lọc theo 1 student ở bất kỳ phía nào (from/to)

    SwapMode mode;
    SwapStatus status;
}
