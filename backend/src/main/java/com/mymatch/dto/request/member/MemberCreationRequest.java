package com.mymatch.dto.request.member;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberCreationRequest {
    Long memberId;
    String name;
    String note;
    Set<Long> skillIds;
}
