package com.example.internetprograming_backend.data.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomMemberPrincipal {

    private Long memberId;

    private String name;

    private String email;

}
