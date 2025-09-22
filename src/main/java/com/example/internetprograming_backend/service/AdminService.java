package com.example.internetprograming_backend.service;

import com.example.internetprograming_backend.common.type.ApprovalRequestType;
import com.example.internetprograming_backend.common.type.ApprovalStatus;
import com.example.internetprograming_backend.data.dto.ApprovalRequestDto;
import com.example.internetprograming_backend.data.dto.jwt.CustomMemberPrincipal;
import com.example.internetprograming_backend.domain.ApprovalRequest;
import com.example.internetprograming_backend.repository.ApprovalRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ApprovalRequestRepository approvalRequestRepository;

    public List<ApprovalRequestDto> getApprovalRequestList(ApprovalStatus approvalStatus) {

        return approvalRequestRepository.findApprovalRequestByApprovalStatus(approvalStatus).stream()
                .map(approvalRequest -> ApprovalRequestDto.builder()
                        .approvalRequestId(approvalRequest.getApprovalRequestId())
                        .approvalRequestType(approvalRequest.getApprovalRequestType())
                        .approvalStatus(approvalRequest.getApprovalStatus())
                        .requestTime(approvalRequest.getRequestTime())
                        .completedTime(approvalRequest.getCompletedTime())
                        .build()
                ).collect(Collectors.toList());
    }
}
