package com.mymatch.service;

import com.mymatch.dto.request.teammember.TeamMemberAddRequest;
import com.mymatch.dto.request.teammember.TeamMemberUpdateRequest;
import com.mymatch.dto.response.teammember.TeamMemberResponse;

import java.util.List;

public interface TeamMemberService {
    TeamMemberResponse addMember(Long teamId, TeamMemberAddRequest req);
    TeamMemberResponse updateMember(Long teamId, Long teamMemberId, TeamMemberUpdateRequest req);
    void deleteMember(Long teamId, Long teamMemberId);
}
