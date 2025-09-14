package com.example.internetprograming_backend.controller;

import com.example.internetprograming_backend.common.Path.MemberEndPoint;
import com.example.internetprograming_backend.common.exception.CustomExceptionResponse;
import com.example.internetprograming_backend.common.type.TokenRole;
import com.example.internetprograming_backend.data.dto.jwt.CustomMemberPrincipal;
import com.example.internetprograming_backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(MemberEndPoint.BASE_MEMBER_PATH)
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(MemberEndPoint.MY_PROFILE_PATH)
    public ResponseEntity<?> getMyProfile(@AuthenticationPrincipal CustomMemberPrincipal customMemberPrincipal) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(memberService.getMyProfile(customMemberPrincipal));
    }

    @GetMapping(MemberEndPoint.REQUEST_ROLE_CHANGE_PATH)
    public ResponseEntity<?> requestRoleChange(@AuthenticationPrincipal CustomMemberPrincipal customMemberPrincipal, @RequestParam TokenRole role) {

        memberService.requestRoleChange(customMemberPrincipal, role);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomExceptionResponse.ROLE_CHANGE_REQUEST_COMPLETE.getMessage());
    }

}
