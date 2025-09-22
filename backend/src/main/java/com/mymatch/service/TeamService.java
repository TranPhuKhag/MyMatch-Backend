package com.mymatch.service;
import com.mymatch.dto.request.team.TeamFilterRequest;
import com.mymatch.dto.request.team.TeamUpdateRequest;
import com.mymatch.dto.request.team.TeamCreationRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.team.TeamResponse;

public interface TeamService {
    TeamResponse getTeamById(Long id);
    TeamResponse createTeam(TeamCreationRequest request);
    TeamResponse updateTeam(Long id, TeamUpdateRequest request);
    void deleteTeam(Long id);
    PageResponse<TeamResponse> getAllTeams(TeamFilterRequest request);

}