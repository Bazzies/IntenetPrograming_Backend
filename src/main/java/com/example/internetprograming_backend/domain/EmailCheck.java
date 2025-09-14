package com.example.internetprograming_backend.domain;

import com.example.internetprograming_backend.common.type.EmailCheckType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_check")
@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emailCheckId;

    private String email;

    private String authCode;

    private EmailCheckType emailCheckType;

    private LocalDateTime startDateTime;

    private LocalDateTime expiredDateTime;

    private LocalDateTime activeDateTime;

    @Builder.Default
    private boolean hasCheck = false;

    public void check() {
        this.hasCheck = true;
        this.activeDateTime = LocalDateTime.now().plusMinutes(30);
    }

}
