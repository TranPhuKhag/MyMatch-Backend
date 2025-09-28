package com.mymatch.service.impl;

import com.mymatch.dto.request.member.MemberCreationRequest;
import com.mymatch.dto.request.team.*;
import com.mymatch.dto.request.teammember.TeamMemberAddRequest;
import com.mymatch.dto.response.PageResponse;
import com.mymatch.dto.response.member.MemberResponse;
import com.mymatch.dto.response.team.TeamResponse;
import com.mymatch.dto.response.teammember.TeamMemberResponse;
import com.mymatch.dto.response.teamrequest.TeamRequestResponse;
import com.mymatch.entity.*;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.*;
import com.mymatch.repository.*;
import com.mymatch.service.*;
import com.mymatch.specification.TeamSpecification;
import com.mymatch.utils.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TeamServiceImpl implements TeamService {
    TeamRepository teamRepository;
    UserRepository userRepository;
    SemesterRepository semesterRepository;
    CampusRepository campusRepository;
    TeamMapper teamMapper;
    CourseRepository courseRepository;
    TeamRequestService teamRequestService;
    TeamMemberService teamMemberService;
    MemberService memberService;
    TeamRequestMapper teamRequestMapper;
    TeamMemberMapper teamMemberMapper;

    @Override
    @Transactional
    public TeamResponse createTeam(TeamCreationRequest req) {
        Long userId = SecurityUtil.getCurrentUserId();
        User current = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (current.getStudent() == null) throw new AppException(ErrorCode.STUDENT_INFO_REQUIRED);
        Semester semester = semesterRepository.findById(req.getSemesterId())
                .orElseThrow(() -> new AppException(ErrorCode.SEMESTER_NOT_FOUND));
        Campus campus = campusRepository.findById(req.getCampusId())
                .orElseThrow(() -> new AppException(ErrorCode.CAMPUS_NOT_FOUND));
        Course course = courseRepository.findById(req.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));
        var team = teamMapper.toEntity(req, semester, campus, course);
        team.setCreatedBy(current.getStudent());
        team = teamRepository.save(team);
        // 1) tạo TeamRequest
        List<TeamRequestResponse> teamRequests = new ArrayList<>();
        if (req.getTeamRequest() != null && !req.getTeamRequest().isEmpty()) {
            for (var r : req.getTeamRequest()) {
                teamRequests.add(teamRequestService.createTeamRequest(team, r));
            }
        }

        // 2) tạo Members mới + add vào Team
        List<TeamMemberResponse> teamMembers = new ArrayList<>();
        if (req.getMembers() != null && !req.getMembers().isEmpty()) {
            for (MemberCreationRequest member : req.getMembers()) {
                MemberResponse memberResponse = memberService.createMember(member);
                teamMembers.add(
                        teamMemberService.addMember(
                                team.getId(),
                                TeamMemberAddRequest.builder().memberId(memberResponse.getId()).build()
                        )
                );
            }
        }
        var res = teamMapper.toResponse(team);
        res.setTeamRequest(teamRequests);
        res.setTeamMember(teamMembers);
        res.setRequestCount(teamRequests != null ? teamRequests.size() : 0);
        res.setMemberCount(teamMembers != null ? teamMembers.size() : 0);
        return res;
    }
    @Override
    @Transactional
    public TeamResponse updateTeam(Long id, TeamUpdateRequest req) {

        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TEAM_NOT_FOUND));
        checkOwnerOrAdmin(team, "team:update");
        teamMapper.update(team, req);
        team.setSemester(semesterRepository.findById(req.getSemesterId())
                .orElseThrow(() -> new AppException(ErrorCode.SEMESTER_NOT_FOUND)));
        team.setCampus(campusRepository.findById(req.getCampusId())
                .orElseThrow(() -> new AppException(ErrorCode.CAMPUS_NOT_FOUND)));
        team.setCourse(courseRepository.findById(req.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND)));
        team = teamRepository.save(team);
        // 1) TeamRequest ops
        if (req.getRequestIdsToDelete() != null && !req.getRequestIdsToDelete().isEmpty()) {
            for (var rId : req.getRequestIdsToDelete()) {
                teamRequestService.deleteTeamRequest(rId);
            }
        }
        if (req.getRequestsToUpdate() != null && !req.getRequestsToUpdate().isEmpty()) {
            for (var r : req.getRequestsToUpdate()) {
                teamRequestService.updateTeamRequest(r.getId(), r);
            }
        }
        if (req.getRequestsToCreate() != null && !req.getRequestsToCreate().isEmpty()) {
            for (var r : req.getRequestsToCreate()) {
                teamRequestService.createTeamRequest(team, r);
            }
        }

        // Members: remove first
        if (req.getTeamMemberIdsToRemove() != null && !req.getTeamMemberIdsToRemove().isEmpty()) {
            for (Long tmId : req.getTeamMemberIdsToRemove()) teamMemberService.deleteMember(id, tmId);
        }
        // Create new members then add
        if (req.getMembersToCreate() != null && !req.getMembersToCreate().isEmpty()) {
            // capacity check...
            for (MemberCreationRequest m : req.getMembersToCreate()) {
                MemberResponse mr = memberService.createMember(m);
                teamMemberService.addMember(id, TeamMemberAddRequest.builder().memberId(mr.getId()).build());
            }
        }
        // sau khi xử lý add/remove TeamRequest & TeamMember
        if (req.getMembersToUpdate() != null && !req.getMembersToUpdate().isEmpty()) {
            for (var mu : req.getMembersToUpdate()) {
                memberService.updateMember(mu);
            }
        }

        var res = teamMapper.toResponse(team);
        var reqs = team.getRequests()==null ? List.<TeamRequestResponse>of()
                : team.getRequests().stream().map(teamRequestMapper::toResponse).toList();
        var tms  = team.getTeamMembers()==null ? List.<TeamMemberResponse>of()
                : team.getTeamMembers().stream().map(teamMemberMapper::toResponse).toList();

        res.setTeamRequest(reqs);
        res.setTeamMember(tms);
        res.setRequestCount(reqs.size());
        res.setMemberCount(tms.size());
        return res;
    }
    @Override
    public TeamResponse getTeamById(Long id) {
        var team = teamRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TEAM_NOT_FOUND));
        var teamResponse = teamMapper.toResponse(team);
        List<TeamRequestResponse> requestResponses = team.getRequests() == null ? Collections.emptyList() :
                team.getRequests().stream()
                        .map(teamRequestMapper::toResponse)
                        .toList();
        List<TeamMemberResponse> memberResponses = team.getTeamMembers() == null ? Collections.emptyList() :
                team.getTeamMembers().stream()
                        .map(teamMemberMapper::toResponse)
                        .toList();
        teamResponse.setTeamRequest(requestResponses);
        teamResponse.setTeamMember(memberResponses);
        teamResponse.setRequestCount( (team.getRequests() == null ? 0 : team.getRequests().size()) );
        teamResponse.setMemberCount( (team.getTeamMembers() == null ? 0 : team.getTeamMembers().size()) );
        return teamResponse;
    }
    @Override
    public void deleteTeam(Long id) {
       Team team = teamRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TEAM_NOT_FOUND));
       checkOwnerOrAdmin(team, "team:delete");
        teamRepository.delete(team);
    }

    @Override
    public PageResponse<TeamResponse> getAllTeams(TeamFilterRequest f) {
        var spec = TeamSpecification.byFilter(f);
        var sort = Sort.by(
                Sort.Direction.fromOptionalString(f.getSortDirection()).orElse(Sort.Direction.DESC),
                (f.getSortBy() == null || f.getSortBy().isBlank()) ? "createAt" : f.getSortBy()
        );
        Pageable pageable = PageRequest.of(Math.max(f.getPage() - 1, 0), Math.max(f.getSize(), 1), sort);
        Page<Team> pages = teamRepository.findAll(spec, pageable);
        List<TeamResponse> data = pages.getContent().stream().map(t -> {
            var r = teamMapper.toResponse(t);

            // map teamRequest
            var reqs = (t.getRequests() == null) ? List.<TeamRequestResponse>of()
                    : t.getRequests().stream().map(teamRequestMapper::toResponse).toList();
            r.setTeamRequest(reqs);

            // set counts
            r.setRequestCount(reqs.size());
            return r;

        }).toList();

        return PageResponse.<TeamResponse>builder()
                .data(data)
                .pageSize(pages.getSize())
                .totalPages(pages.getTotalPages())
                .totalElements(pages.getTotalElements())
                .currentPage(f.getPage())
                .build();
    }
    private void checkOwnerOrAdmin(Team team, String authority) {
        Long currentUserId = SecurityUtil.getCurrentUserId();
        boolean isOwner = team.getCreatedBy() != null
                && team.getCreatedBy().getUser() != null
                && team.getCreatedBy().getUser().getId().equals(currentUserId);
        boolean hasPerm = SecurityUtil.hasAuthority(authority);
        if (!isOwner && !hasPerm) throw new AppException(ErrorCode.ACCESS_DENIED);
    }
}
