package com.mymatch.service.impl;

import com.mymatch.dto.request.teamrequest.TeamRequestCreationRequest;
import com.mymatch.dto.request.teamrequest.TeamRequestUpdateRequest;
import com.mymatch.dto.response.teamrequest.TeamRequestResponse;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.TeamRequestMapper;
import com.mymatch.repository.TeamRequestRepository;
import com.mymatch.service.TeamRequestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import com.mymatch.repository.*;
import com.mymatch.entity.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TeamRequestServiceImpl implements TeamRequestService {
    TeamRequestRepository teamRequestRepository;
    TeamRequestMapper teamRequestMapper;
    SkillRepository skillRepository;

    @Override
    public TeamRequestResponse createTeamRequest(Team team, TeamRequestCreationRequest req) {

        TeamRequest tr = teamRequestMapper.toEntity(req);
        tr.setTeam(team);
        if (tr.getSkills() == null) tr.setSkills(new HashSet<>());

        // SkillIds optional
        if (req.getSkillIds() != null && !req.getSkillIds().isEmpty()) {
            Set<Long> incoming = new HashSet<>(req.getSkillIds()); // distinct
            var skills = skillRepository.findAllById(incoming);
            if (skills.size() != incoming.size()) {
                throw new AppException(ErrorCode.INVALID_PARAMETER); // hoặc INVALID_PARAMETER
            }
            for (Skill s : skills) {
                tr.getSkills().add(
                        TeamRequestSkill.builder()
                                .teamRequest(tr)
                                .skill(s)
                                .build()
                );
            }
        }

        TeamRequestResponse teamRequestResponse = teamRequestMapper.toResponse(teamRequestRepository.save(tr));
        return teamRequestResponse;
    }

    @Override
    public TeamRequestResponse updateTeamRequest(Long requestId, TeamRequestUpdateRequest req) {
        TeamRequest tr = teamRequestRepository.findById(requestId)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_FOUND));
        if (req.getId() != null && !req.getId().equals(requestId)) {
            throw new AppException(ErrorCode.INVALID_PARAMETER);
        }
        teamRequestMapper.update(tr, req);

        if (req.getSkillIds() != null) {
            if (tr.getSkills() == null) tr.setSkills(new HashSet<>());
            Set<Long> newIds = new HashSet<>(req.getSkillIds());
            // remove cái không còn trong newIds
            tr.getSkills().removeIf(x -> !newIds.contains(x.getSkill().getId()));
            // còn lại: thêm những id mới
            Collectors Collectors = null;
            Set<Long> currentIds = tr.getSkills().stream()
                    .map(x -> x.getSkill().getId())
                    .collect(Collectors.toSet());
            newIds.removeAll(currentIds);
            if (!newIds.isEmpty()) {
                var skillsToAdd = skillRepository.findAllById(newIds);
                if (skillsToAdd.size() != newIds.size()) {
                    throw new AppException(ErrorCode.INVALID_PARAMETER);
                }
                for (Skill s : skillsToAdd) {
                    tr.getSkills().add(
                            TeamRequestSkill.builder()
                                    .teamRequest(tr)
                                    .skill(s)
                                    .build()
                    );
                }
            }
        }
        tr = teamRequestRepository.save(tr);
        return teamRequestMapper.toResponse(tr);
    }

    @Override
    public void deleteTeamRequest(Long requestId) {
        TeamRequest tr = teamRequestRepository.findById(requestId)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_FOUND));
        teamRequestRepository.delete(tr);
    }
}
