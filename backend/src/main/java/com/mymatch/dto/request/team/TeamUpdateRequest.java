package com.mymatch.dto.request.team;

import com.mymatch.dto.request.member.MemberCreationRequest;
import com.mymatch.dto.request.teammember.TeamMemberUpdateRequest;
import com.mymatch.dto.request.teamrequest.TeamRequestCreationRequest;
import com.mymatch.dto.request.teamrequest.TeamRequestUpdateRequest;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamUpdateRequest {
    @NotBlank String name;
    @Positive @Max(50) Integer memberMax;
    @Size(max = 1000) String description;

    @NotNull Long semesterId;
    @NotNull Long campusId;
    @NotNull Long courseId;

    // ---- TeamRequest ops ----
    List<TeamRequestCreationRequest>   requestsToCreate;  // thêm mới
    List<TeamRequestUpdateRequest>     requestsToUpdate;  // update title/urgency/skills
    List<Long>                         requestIdsToDelete; // xoá theo id

    // ---- TeamMember ops ----
    List<Long> teamMemberIdsToRemove; // remove theo team_member.id

    List<MemberCreationRequest> newMembersToCreateAndAdd;
    List<MemberCreationRequest> members;
}
