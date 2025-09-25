package com.mymatch.service.impl;

import com.mymatch.dto.request.member.MemberCreationRequest;
import com.mymatch.dto.response.member.MemberResponse;
import com.mymatch.dto.response.member.MemberSkillResponse;
import com.mymatch.entity.*;
import com.mymatch.exception.AppException;
import com.mymatch.exception.ErrorCode;
import com.mymatch.mapper.MemberMapper;
import com.mymatch.mapper.MemberSkillMapper;
import com.mymatch.repository.MemberRepository;
import com.mymatch.repository.MemberSkillRepository;
import com.mymatch.repository.SkillRepository;
import com.mymatch.service.MemberService;
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
    MemberSkillMapper memberSkillMapper;

    @Override
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

}
