package com.example.internetprograming_backend.controller;

import com.example.internetprograming_backend.common.path.AdminEndPoint;
import com.example.internetprograming_backend.common.type.ApprovalStatus;
import com.example.internetprograming_backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AdminEndPoint.BASE_ADMIN_PATH)
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping(AdminEndPoint.ADMIN_APPROVAL_REQUEST_LIST_PATH)
    public ResponseEntity<?> getApprovalRequestList(
            @RequestParam ApprovalStatus approvalStatus
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(adminService.getApprovalRequestList(approvalStatus));
    }
}
