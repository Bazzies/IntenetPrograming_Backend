package com.example.internetprograming_backend.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApprovalStatus {

    APPROVED("APPROVED", "승인"),
    REJECTED("REJECTED", "거절"),
    PENDING("PENDING", "대기");

    private final String approval;
    private final String description;
}
