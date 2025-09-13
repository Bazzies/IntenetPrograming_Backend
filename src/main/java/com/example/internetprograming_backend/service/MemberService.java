package com.example.internetprograming_backend.service;

import com.example.internetprograming_backend.common.exception.CustomException;
import com.example.internetprograming_backend.common.exception.CustomExceptionResponse;
import com.example.internetprograming_backend.common.type.TokenRole;
import com.example.internetprograming_backend.data.dto.MyProfileDto;
import com.example.internetprograming_backend.data.dto.jwt.CustomMemberPrincipal;
import com.example.internetprograming_backend.domain.Member;
import com.example.internetprograming_backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MyProfileDto getMyProfile(CustomMemberPrincipal customMemberPrincipal) {
        Member member = memberRepository.findById(customMemberPrincipal.getMemberId())
                .orElseThrow(() -> new CustomException(CustomExceptionResponse.NOT_FOUND_MEMBER));

        return MyProfileDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .highestRole(TokenRole.highestRole(member.getMemberRoleSet()))
                .hasPrivacyConsent(member.isHasPrivacyConsent())
                .build();
    }


}
