package com.example.internetprograming_backend.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailCheckType {

    SIGNUP("회원가입"),
    FIND_ACCOUNT_ID("아이디 찾기"),
    RESET_PASSWORD("비밀번호 찾기");

    private final String description;
}
