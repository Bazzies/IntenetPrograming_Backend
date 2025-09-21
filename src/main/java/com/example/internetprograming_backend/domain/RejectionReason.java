package com.example.internetprograming_backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
public class RejectionReason {

    @Id
    @Column(name = "rejection_reasion_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rejectionReasonId;

    private String comment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_request_id")
    private ApprovalRequest approvalRequest;
}