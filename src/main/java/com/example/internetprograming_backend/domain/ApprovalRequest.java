package com.example.internetprograming_backend.domain;

import com.example.internetprograming_backend.common.type.ApprovalRequestType;
import com.example.internetprograming_backend.common.type.ApprovalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalRequest {

    @Id
    @Column(name = "approval_request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long approvalRequestId;

    private ApprovalRequestType approvalRequestType;

    private String previousValue; // 이전 값

    private String requestedValue; // 변경을 요청한 값

    private ApprovalStatus approvalStatus;

    private LocalDateTime requestTime;

    private LocalDateTime completedTime; // 요청이 최종 완료된 시간

    @Builder.Default
    private boolean hasUrgency = false;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "approvalRequest")
    private RejectionReason rejectionReason;

    public void completeApprovalRequest() {
        this.completedTime = LocalDateTime.now();
    }
}
