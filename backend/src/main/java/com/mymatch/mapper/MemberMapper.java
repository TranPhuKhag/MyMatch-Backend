package com.mymatch.mapper;

import com.mymatch.dto.request.member.MemberCreationRequest;
import com.mymatch.dto.response.member.MemberResponse;
import com.mymatch.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = {MemberSkillMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MemberMapper {
    Member toMember(MemberCreationRequest request);
    MemberResponse toMemberResponse(Member member);
}
