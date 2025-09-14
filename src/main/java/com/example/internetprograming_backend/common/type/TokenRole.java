package com.example.internetprograming_backend.common.type;

import com.example.internetprograming_backend.domain.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public enum TokenRole {

    MEMBER("ROLE_MEMBER", "회원"),
    MANAGER("ROLE_MANAGER", "관리자"),
    ADMIN("ROLE_ADMIN", "시스템 관리자");

    private final String tokenRole;
    private final String tokenRoleName;

    public static List<TokenRole> getIncludeRole(TokenRole tokenRole) {

        List<TokenRole> tokenRoleList = new ArrayList<>();

        for (TokenRole role : TokenRole.values()) {
            if (tokenRole.compareTo(role) >= 0) {
                tokenRoleList.add(role);
            }
        }

        return tokenRoleList;
    }

    public static TokenRole highestRole(Set<MemberRole> memberRoleSet) {
        return memberRoleSet.stream()
                .map(MemberRole::getTokenRole)
                .max(Enum::compareTo)
                .orElse(TokenRole.MEMBER);
    }
}
