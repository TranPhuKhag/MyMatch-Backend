package com.mymatch.service;

import com.mymatch.dto.request.teamrequest.TeamRequestCreationRequest;
import com.mymatch.dto.request.teamrequest.TeamRequestUpdateRequest;
import com.mymatch.dto.response.teamrequest.TeamRequestResponse;
import com.mymatch.entity.Team;

public interface TeamRequestService {
    TeamRequestResponse createTeamRequest(Team team, TeamRequestCreationRequest req);
    TeamRequestResponse updateTeamRequest (Long requestId, TeamRequestUpdateRequest req);
    void deleteTeamRequest (Long requestId);
}
