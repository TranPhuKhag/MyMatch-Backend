package com.mymatch.dto.request.team;

import com.mymatch.dto.request.member.MemberCreationRequest;
import com.mymatch.dto.request.teammember.TeamMemberAddRequest;
import com.mymatch.dto.request.teamrequest.TeamRequestCreationRequest;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamCreationRequest {
    @NotBlank String name;
    @Positive @Max(50) Integer memberMax;
    @Size(max = 1000) String description;

    @NotNull Long semesterId;
    @NotNull Long campusId;
    @NotNull Long courseId;
    String image;

    @NotNull
    List<TeamRequestCreationRequest> teamRequest;

    List<MemberCreationRequest> members;

}
