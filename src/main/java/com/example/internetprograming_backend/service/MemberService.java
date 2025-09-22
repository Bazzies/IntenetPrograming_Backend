package com.example.internetprograming_backend.service;

import com.example.internetprograming_backend.common.email.EmailMessage;
import com.example.internetprograming_backend.common.email.utils.EmailUtils;
import com.example.internetprograming_backend.common.exception.CustomException;
import com.example.internetprograming_backend.common.exception.CustomExceptionResponse;
import com.example.internetprograming_backend.common.type.ApprovalRequestType;
import com.example.internetprograming_backend.common.type.ApprovalStatus;
import com.example.internetprograming_backend.common.type.TokenRole;
import com.example.internetprograming_backend.data.dto.MyProfileDto;
import com.example.internetprograming_backend.data.dto.UpdateMyProfile;
import com.example.internetprograming_backend.data.dto.jwt.CustomMemberPrincipal;
import com.example.internetprograming_backend.data.dto.jwt.RoleChangeRequestForm;
import com.example.internetprograming_backend.domain.ApprovalRequest;
import com.example.internetprograming_backend.domain.Member;
import com.example.internetprograming_backend.repository.ApprovalRequestRepository;
import com.example.internetprograming_backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final ApprovalRequestRepository approvalRequestRepository;

    private final EmailUtils emailUtils;

    @Value("${spring.mail.username}")
    private String ADMIN_EMAIL;

    @Transactional
    public MyProfileDto getMyProfile(CustomMemberPrincipal customMemberPrincipal) {
        Member member = memberRepository.findById(customMemberPrincipal.getMemberId())
                .orElseThrow(() -> new CustomException(CustomExceptionResponse.NOT_FOUND_MEMBER));

        return MyProfileDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .highestRole(TokenRole.highestRole(member.getMemberRoleSet()).getTokenRoleName())
                .hasPrivacyConsent(member.isHasPrivacyConsent())
                .build();
    }

    @Transactional
    public void updateMyProfile(CustomMemberPrincipal customMemberPrincipal, UpdateMyProfile updateMyProfile) {
        Member member = memberRepository.findById(customMemberPrincipal.getMemberId())
                .orElseThrow(() -> new CustomException(CustomExceptionResponse.NOT_FOUND_MEMBER));

        memberRepository.save(member.updateInfo(updateMyProfile));
    }

    @Transactional
    public void requestRoleChange(CustomMemberPrincipal customMemberPrincipal, RoleChangeRequestForm roleChangeRequestForm) {
        Member member = memberRepository.findById(customMemberPrincipal.getMemberId())
                .orElseThrow(() -> new CustomException(CustomExceptionResponse.NOT_FOUND_MEMBER));

        TokenRole currentMemberRole = TokenRole.highestRole(member.getMemberRoleSet());

        if (currentMemberRole.equals(TokenRole.ADMIN)) {
            throw new CustomException(CustomExceptionResponse.CANNOT_CHANGE_ADMIN_ROLE);
        } else if (roleChangeRequestForm.getRequestRole().equals(TokenRole.ADMIN)) {
            throw new CustomException(CustomExceptionResponse.CANNOT_CHANGE_TO_ADMIN_ROLE);
        } else if (currentMemberRole.equals(roleChangeRequestForm.getRequestRole())) {
            throw new CustomException(CustomExceptionResponse.CANNOT_CHANGE_TO_SAME_ROLE);
        }

        EmailMessage emailMessage = EmailMessage.builder()
                .to(ADMIN_EMAIL)
                .subject("[InternetProgramming Service] 권한 변경 요청")
                .message("권한 변경 요청" +
                        "\n회원 이름 : " + member.getName() +
                        "\n회원 이메일 : " + member.getEmail() +
                        "\n현재 회원 권한 : " + currentMemberRole.getTokenRoleName() +
                        "\n변경 요청 권한 : " + roleChangeRequestForm.getRequestRole()
                )
                .build();

        emailUtils.sendSimpleMessage(emailMessage);

        ApprovalRequest approvalRequest = ApprovalRequest.builder()
                .approvalRequestType(ApprovalRequestType.PERMISSION_CHANGE)
                .previousValue(TokenRole.highestRole(member.getMemberRoleSet()).name())
                .requestedValue(roleChangeRequestForm.getRequestRole().name())
                .approvalStatus(ApprovalStatus.PENDING)
                .requestTime(LocalDateTime.now())
                .member(member)
                .build();

        approvalRequestRepository.save(approvalRequest);

    }


}
