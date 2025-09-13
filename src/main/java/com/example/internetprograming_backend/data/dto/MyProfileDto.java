package com.example.internetprograming_backend.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyProfileDto {

    private String email;

    private String name;

    private boolean hasPrivacyConsent;

    private String highestRole;

}
