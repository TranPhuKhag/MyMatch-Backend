package com.mymatch.dto.request.teammember;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamMemberUpdateRequest {
    @NotNull Long teamMemberId; // entity TeamMember
    @NotNull Long memberId;
}
