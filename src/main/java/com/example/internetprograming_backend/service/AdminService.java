package com.example.internetprograming_backend.service;

import com.example.internetprograming_backend.common.exception.CustomException;
import com.example.internetprograming_backend.common.exception.CustomExceptionResponse;
import com.example.internetprograming_backend.common.type.ApprovalRequestType;
import com.example.internetprograming_backend.common.type.ApprovalStatus;
import com.example.internetprograming_backend.common.type.TokenRole;
import com.example.internetprograming_backend.data.dto.ApprovalRequestDetailDto;
import com.example.internetprograming_backend.data.dto.ApprovalRequestDto;
import com.example.internetprograming_backend.data.dto.jwt.CustomMemberPrincipal;
import com.example.internetprograming_backend.domain.ApprovalRequest;
import com.example.internetprograming_backend.domain.Member;
import com.example.internetprograming_backend.domain.MemberRole;
import com.example.internetprograming_backend.repository.ApprovalRequestRepository;
import com.example.internetprograming_backend.repository.MemberRepository;
import com.example.internetprograming_backend.repository.MemberRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ApprovalRequestRepository approvalRequestRepository;
    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;

    public List<ApprovalRequestDto> getApprovalRequestList(ApprovalStatus approvalStatus) {

        return approvalRequestRepository.findApprovalRequestByApprovalStatus(approvalStatus).stream()
                .map(approvalRequest -> ApprovalRequestDto.builder()
                        .approvalRequestId(approvalRequest.getApprovalRequestId())
                        .approvalRequestType(approvalRequest.getApprovalRequestType())
                        .approvalStatus(approvalRequest.getApprovalStatus())
                        .requestTime(approvalRequest.getRequestTime())
                        .completedTime(approvalRequest.getCompletedTime())
                        .hasUrgency(approvalRequest.isHasUrgency())
                        .build()
                ).collect(Collectors.toList());
    }

    public ApprovalRequestDetailDto getApprovalRequestDetail(Long approvalRequestId) {
        ApprovalRequest approvalRequest = approvalRequestRepository.findById(approvalRequestId)
                .orElseThrow(() -> new CustomException(CustomExceptionResponse.NOT_FOUND_APPROVAL_REQUEST));

        return ApprovalRequestDetailDto.builder()
                .approvalRequestId(approvalRequest.getApprovalRequestId())
                .approvalRequestType(approvalRequest.getApprovalRequestType())
                .approvalStatus(approvalRequest.getApprovalStatus())
                .previousValue(approvalRequest.getPreviousValue())
                .requestedValue(approvalRequest.getRequestedValue())
                .requestTime(approvalRequest.getRequestTime())
                .completedTime(approvalRequest.getCompletedTime())
                .hasUrgency(approvalRequest.isHasUrgency())
                .build();
    }

    @Transactional
    public void approvedRequestHandler(Long approvalRequestId) {
        ApprovalRequest approvalRequest = approvalRequestRepository.findById(approvalRequestId)
                .orElseThrow(() -> new CustomException(CustomExceptionResponse.NOT_FOUND_APPROVAL_REQUEST));

        Member member = approvalRequest.getMember();

        switch (approvalRequest.getApprovalRequestType()) {
            case PERMISSION_CHANGE -> {
                updateRole(member, approvalRequest.getRequestedValue());
                approvalRequest.completeApprovalRequest();
            }
            default -> {
                throw new CustomException(CustomExceptionResponse.INVALID_APPROVAL_REQUEST_TYPE);
            }
        }
        approvalRequestRepository.save(approvalRequest);
    }

    @Transactional
    public void rejectRequestHandler(Long approvalRequestId) {

    }

    @Transactional
    public void updateRole(Member member,  String requestedValue) {
        memberRoleRepository.deleteAllByMemberInBatch(member);

        List<TokenRole> newTokenRoleList = TokenRole.getIncludeRole(requestedValue);

        memberRoleRepository.saveAll(newTokenRoleList.stream()
                .map(tokenRole -> MemberRole.builder()
                        .tokenRole(tokenRole)
                        .member(member)
                        .createAt(LocalDateTime.now())
                        .build()
                ).collect(Collectors.toSet())
        );
    }
}
