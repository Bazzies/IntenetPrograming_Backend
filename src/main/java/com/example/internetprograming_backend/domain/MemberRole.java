package com.example.internetprograming_backend.domain;

import com.example.internetprograming_backend.common.type.TokenRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder @Getter
@Table(name = "member_role")
@NoArgsConstructor
@AllArgsConstructor
public class MemberRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberRoleId;

    @Enumerated(EnumType.STRING)
    private TokenRole tokenRole;

    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
