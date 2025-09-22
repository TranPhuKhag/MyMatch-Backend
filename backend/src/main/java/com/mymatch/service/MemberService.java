package com.mymatch.service;


import com.mymatch.dto.request.member.MemberCreationRequest;
import com.mymatch.dto.response.member.MemberResponse;

public interface MemberService {
    MemberResponse createMember(MemberCreationRequest request);
}
