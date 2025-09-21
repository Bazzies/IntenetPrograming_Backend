package com.example.internetprograming_backend.domain;

import com.example.internetprograming_backend.data.dto.UpdateMyProfile;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Builder @Getter
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
public class Member implements UserDetails {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Builder.Default
    private boolean hasEnabled = true;

    @Builder.Default
    private boolean withdraw = false;

    @Builder.Default
    private boolean hasPrivacyConsent = false;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private Set<MemberRole> memberRoleSet = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ApprovalRequest> approvalRequestList = new ArrayList<>();

    public void withdrawMember() {
        this.withdraw = true;
    }

    public Member addTokenRoleSet(Set<MemberRole> memberRoleSet) {
        this.memberRoleSet.addAll(memberRoleSet);
        return this;
    }

    public Member updateInfo(UpdateMyProfile updateMyProfile) {
        this.name = updateMyProfile.getName();
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (MemberRole memberRole : memberRoleSet) {
            grantedAuthorities.add(new SimpleGrantedAuthority(memberRole.getTokenRole().getTokenRole()));
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.name;
    }
}
