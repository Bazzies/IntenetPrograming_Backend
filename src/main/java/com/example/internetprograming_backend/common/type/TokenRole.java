package com.example.internetprograming_backend.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public enum TokenRole {

    MEMBER("ROLE_MEMBER", "회원"),
    MANAGER("ROLE_MANAGER", "관리자"),
    ADMIN("ROLE_ADMIN", "시스템 관리자");

    private final String tokenRole;
    private final String tokenRoleName;

    public List<TokenRole> getIncludeRole(TokenRole tokenRole) {

        List<TokenRole> tokenRoleList = new ArrayList<>();

        for (TokenRole role : TokenRole.values()) {
            if (tokenRole.compareTo(role) >= 0) {
                tokenRoleList.add(role);
            }
        }

        return tokenRoleList;
    }
}
