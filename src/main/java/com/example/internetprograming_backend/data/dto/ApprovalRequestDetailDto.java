package com.example.internetprograming_backend.data.dto;

import com.example.internetprograming_backend.common.type.ApprovalRequestType;
import com.example.internetprograming_backend.common.type.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalRequestDetailDto {

    private Long approvalRequestId;

    private ApprovalRequestType approvalRequestType;

    private ApprovalStatus approvalStatus;

    private String previousValue;

    private String requestedValue;

    private LocalDateTime requestTime;

    private LocalDateTime completedTime; // 요청이 최종 완료된 시간

    private boolean hasUrgency;

}

