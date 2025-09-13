package com.example.internetprograming_backend.data.form;


import com.example.internetprograming_backend.common.type.TokenRole;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignUpForm {

    private String email;

    private String password;

    private String name;

    private boolean hasPrivacyConsent;

    private TokenRole role;

}
