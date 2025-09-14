package com.example.internetprograming_backend.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMyProfile {

    private String name;

    private String email;

    private boolean hasPrivacyConsent;

}
