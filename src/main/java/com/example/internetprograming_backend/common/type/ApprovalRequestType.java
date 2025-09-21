package com.example.internetprograming_backend.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApprovalRequestType {

    PERMISSION_CHANGE("PERMISSION_CHANGE", "권한 변경 요청"),;

    private final String type;
    private final String description;
}
