package com.example.internetprograming_backend.data.form;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignUpForm {

    private String email;

    private String password;

    private String name;

    private boolean hasPrivacyConsent;

}
