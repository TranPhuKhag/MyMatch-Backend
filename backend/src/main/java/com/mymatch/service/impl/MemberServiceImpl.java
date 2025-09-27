package com.mymatch.service.impl;

import com.mymatch.dto.request.member.MemberCreationRequest;
import com.mymatch.dto.request.member.MemberUpdateRequest;
import com.mymatch.dto.response.member.MemberResponse;
import com.mymatch.entity.*;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.MemberMapper;
import com.mymatch.repository.MemberRepository;
import com.mymatch.repository.MemberSkillRepository;
import com.mymatch.repository.SkillRepository;
import com.mymatch.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberServiceImpl implements MemberService {
    MemberMapper memberMapper;
    MemberRepository memberRepository;
    SkillRepository skillRepository;
    MemberSkillRepository memberSkillRepository;

    @Override
    @Transactional
    public MemberResponse createMember(MemberCreationRequest request) {
        Member member = memberMapper.toMember(request);
        member = memberRepository.save(member);
        if (member.getMemberSkills() == null) member.setMemberSkills(new HashSet<>());

        if (request.getSkillIds() != null && !request.getSkillIds().isEmpty()) {
            var skills = skillRepository.findAllById(request.getSkillIds());
            if (skills.size() != request.getSkillIds().size()) {
                throw new AppException(ErrorCode.INVALID_PARAMETER);
            }
            for (Skill s : skills) {
                member.getMemberSkills().add(
                        memberSkillRepository.save(
                        MemberSkill.builder()
                                .member(member)
                                .skill(s).build()
                ));
            }
        }
        return memberMapper.toMemberResponse(member);
    }
    @Override
    @Transactional
    public MemberResponse updateMember(MemberUpdateRequest request) {
        if (request.getId() == null) throw new AppException(ErrorCode.INVALID_PARAMETER);

        Member member = memberRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND));

        // 1) update các field cơ bản
        memberMapper.update(member, request);

        // 2) đồng bộ skill nếu client gửi skillIds
        if (request.getSkillIds() != null) {
            if (member.getMemberSkills() == null) member.setMemberSkills(new HashSet<>());

            // set hiện tại
            var currentIds = member.getMemberSkills().stream()
                    .map(ms -> ms.getSkill().getId())
                    .collect(java.util.stream.Collectors.toSet());

            // set mới
            var newIds = new java.util.HashSet<Long>(request.getSkillIds());

            // a) remove những skill không còn trong newIds
            member.getMemberSkills().removeIf(ms -> !newIds.contains(ms.getSkill().getId()));
            // (orphanRemoval = true trên Member.memberSkills sẽ HARD DELETE MemberSkill)

            newIds.removeAll(currentIds);
            if (!newIds.isEmpty()) {
                var skillsToAdd = skillRepository.findAllById(newIds);
                if (skillsToAdd.size() != newIds.size()) {
                    throw new AppException(ErrorCode.INVALID_PARAMETER);
                }
                for (Skill skill : skillsToAdd) {
                    member.getMemberSkills().add(
                            MemberSkill.builder().member(member).skill(skill).build()
                    );
                }
            }
        }

        member = memberRepository.save(member);
        return memberMapper.toMemberResponse(member);
    }
}
