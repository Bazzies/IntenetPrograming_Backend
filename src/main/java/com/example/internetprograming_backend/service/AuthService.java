package com.example.internetprograming_backend.service;

import com.example.internetprograming_backend.common.email.EmailMessage;
import com.example.internetprograming_backend.common.email.utils.EmailUtils;
import com.example.internetprograming_backend.common.exception.CustomException;
import com.example.internetprograming_backend.common.exception.CustomExceptionResponse;
import com.example.internetprograming_backend.common.jwt.JwtTokenProvider;
import com.example.internetprograming_backend.common.type.EmailCheckType;
import com.example.internetprograming_backend.common.type.TokenRole;
import com.example.internetprograming_backend.data.dto.jwt.JwtToken;
import com.example.internetprograming_backend.data.form.SignInForm;
import com.example.internetprograming_backend.data.form.SignUpForm;
import com.example.internetprograming_backend.domain.EmailCheck;
import com.example.internetprograming_backend.domain.Member;
import com.example.internetprograming_backend.domain.MemberRole;
import com.example.internetprograming_backend.repository.EmailCheckRepository;
import com.example.internetprograming_backend.repository.MemberRepository;
import com.example.internetprograming_backend.repository.MemberRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final EmailCheckRepository emailCheckRepository;

    private final EmailUtils emailUtils;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${server.ip}")
    private String SERVER_IP;

    @Transactional
    public void signUp(SignUpForm signUpForm) {
        if (memberRepository.existsMemberByEmail(signUpForm.getEmail())) {
            throw new CustomException(CustomExceptionResponse.EMAIL_ALREADY_USED);
        }

        EmailCheck emailCheck = emailCheckRepository.findTopByEmailAndEmailCheckTypeAndHasCheckIsTrueOrderByStartDateTimeDesc(signUpForm.getEmail(), EmailCheckType.SIGNUP)
                .orElseThrow(() -> new CustomException(CustomExceptionResponse.NOT_FOUND_EMAIL_VERIFICATION));
        if (!emailCheck.isHasCheck()) {
            throw new CustomException(CustomExceptionResponse.INVALID_EMAIL_VERIFICATION);
        }

        Member member = Member.builder()
                .name(signUpForm.getName())
                .email(signUpForm.getEmail())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .build();

        Set<MemberRole> memberRoleSet = TokenRole.getIncludeRole(signUpForm.getRole()).stream()
                .map(tokenRole -> MemberRole.builder()
                        .tokenRole(tokenRole)
                        .createAt(LocalDateTime.now())
                        .member(member)
                        .build())
                .collect(Collectors.toSet());

        memberRepository.save(member.addTokenRoleSet(memberRoleSet));
    }

    @Transactional
    public void sendSignUpEmail(String email) {
        if (memberRepository.existsMemberByEmailAndWithdrawIsFalse(email)) {
            throw new CustomException(CustomExceptionResponse.MEMBER_ALREADY_REGISTERED);
        }

        String randUUID = UUID.randomUUID().toString();

        EmailMessage emailMessage = EmailMessage.builder()
                .to(email)
                .subject("[InternetProgramming Service] 회원가입 이메일 인증")
                .message("회원가입을 완료하기 위해서는 다음 링크를 클릭해주세요.\n인증 메일의 유효시간은 발송 시점부터 5분간 유효합니다.\n" + SERVER_IP + "/auth/sign-up/email/check/" + randUUID)
                .build();

        emailUtils.sendSimpleMessage(emailMessage);

        LocalDateTime now = LocalDateTime.now();
        EmailCheck emailCheck = EmailCheck.builder()
                .email(email)
                .authCode(randUUID)
                .emailCheckType(EmailCheckType.SIGNUP)
                .startDateTime(now)
                .expiredDateTime(now.plusMinutes(5))
                .build();

        emailCheckRepository.save(emailCheck);
    }

    @Transactional
    public void checkSignUpEmail(String authCode) {
        EmailCheck emailCheck = emailCheckRepository.findTopByAuthCodeAndEmailCheckTypeOrderByStartDateTimeDesc(authCode, EmailCheckType.SIGNUP)
                .orElseThrow(() -> new CustomException(CustomExceptionResponse.NOT_FOUND_EMAIL_VERIFICATION));
        if (emailCheck.getExpiredDateTime().isBefore(LocalDateTime.now()))
            throw new CustomException(CustomExceptionResponse.EXPIRED_EMAIL_AUTH_CODE);
        else if (emailCheck.isHasCheck())
            throw new CustomException(CustomExceptionResponse.EMAIL_ALREADY_VERIFICATION);

        emailCheck.check();

        emailCheckRepository.save(emailCheck);
    }

    @Transactional
    public JwtToken signIn(SignInForm signInForm) {
        Member member = memberRepository.findMemberByEmailAndWithdrawIsFalse(signInForm.getEmail())
                .orElseThrow(() -> new CustomException(CustomExceptionResponse.NOT_FOUND_MEMBER));

        if (!passwordEncoder.matches(signInForm.getPassword(), member.getPassword())) {
            throw new CustomException(CustomExceptionResponse.NOT_MATCH_PASSWORD);
        }

        return JwtToken.builder()
                .accessToken(jwtTokenProvider.createAccessToken(member.getMemberId(), member.getMemberRoleSet()))
                .refreshToken(jwtTokenProvider.createRefreshToken(member.getMemberId(), member.getMemberRoleSet()))
                .build();
    }


}
