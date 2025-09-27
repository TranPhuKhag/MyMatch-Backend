package com.mymatch.service.impl;

import com.mymatch.dto.request.teammember.TeamMemberAddRequest;
import com.mymatch.dto.request.teammember.TeamMemberUpdateRequest;
import com.mymatch.dto.response.teammember.TeamMemberResponse;
import com.mymatch.entity.Member;
import com.mymatch.entity.Team;
import com.mymatch.entity.TeamMember;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.TeamMemberMapper;
import com.mymatch.repository.TeamMemberRepository;
import com.mymatch.repository.*;
import com.mymatch.service.TeamMemberService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;


import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TeamMemberServiceImpl implements TeamMemberService {
    TeamRepository teamRepository;
    TeamMemberRepository teamMemberRepository;
    MemberRepository memberRepository;
    TeamMemberMapper teamMemberMapper;

    @Override
    public TeamMemberResponse addMember(Long teamId, TeamMemberAddRequest req) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new AppException(ErrorCode.TEAM_NOT_FOUND));
        Member member = memberRepository.findById(req.getMemberId())
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND));
        // kiểm tra đã là thành viên chưa
        int current = team.getTeamMembers() == null ? 0 : team.getTeamMembers().size();
        if (team.getMemberMax() != null && current >= team.getMemberMax()) {
            throw new AppException(ErrorCode.TEAM_MEMBER_FULL);
        }

        TeamMember teamMember = TeamMember.builder().team(team).member(member).build();
        teamMember = teamMemberRepository.save(teamMember);
        if (team.getTeamMembers() != null) {
            TeamMember finalTeamMember = teamMember;
            boolean exists = team.getTeamMembers().stream()
                    .anyMatch(tm -> tm.getId().equals(finalTeamMember.getId()));
            if (!exists) {
                team.getTeamMembers().add(teamMember);
            }
        }
        return teamMemberMapper.toResponse(teamMember);
    }

    public TeamMemberResponse updateMember(Long teamId, Long teamMemberId, TeamMemberUpdateRequest req) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new AppException(ErrorCode.TEAM_NOT_FOUND));

        TeamMember teamMember = teamMemberRepository.findById(teamMemberId)
                .orElseThrow(() -> new AppException(ErrorCode.TEAM_MEMBER_NOT_FOUND));

        if (!teamMember.getTeam().getId().equals(teamId)) {
            throw new AppException(ErrorCode.TEAM_MEMBER_NOT_FOUND); // không thuộc team này
        }

        // Nếu memberId mới trùng member hiện tại thì coi như không đổi
        Long currentMemberId = teamMember.getMember() != null ? teamMember.getMember().getId() : null;
        if (!req.getMemberId().equals(currentMemberId)) {
            // 1) Tồn tại member mới?
            Member newMember = memberRepository.findById(req.getMemberId())
                    .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND));

            teamMember.setMember(newMember);
        }
        teamMember = teamMemberRepository.save(teamMember);
        return teamMemberMapper.toResponse(teamMember);
    }

    @Override
    public void deleteMember(Long teamId, Long teamMemberId) {
        TeamMember teamMember = teamMemberRepository.findById(teamMemberId)
                .orElseThrow(() -> new AppException(ErrorCode.TEAM_MEMBER_NOT_FOUND));
        if (!teamMember.getTeam().getId().equals(teamId)) throw new AppException(ErrorCode.TEAM_MEMBER_NOT_FOUND);
        Team team = teamMember.getTeam();
        if (team.getTeamMembers()!=null) team.getTeamMembers().removeIf(x -> x.getId().equals(teamMemberId));
        teamMemberRepository.delete(teamMember);
        boolean stillUsed = teamMemberRepository.existsByMember_Id(teamMember.getMember().getId());
        if (!stillUsed) {
            memberRepository.deleteById(teamMember.getMember().getId());
        }
    }
}
