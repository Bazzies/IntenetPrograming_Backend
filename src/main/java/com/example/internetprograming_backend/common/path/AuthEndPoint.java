package com.example.internetprograming_backend.common.path;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthEndPoint {

    // Base
    public static final String BASE_AUTH_PATH = "/auth";

    // URI
    public static final String SIGNUP_PATH = "/sign-up";
    public static final String SIGN_IN_PATH = "/sign-in";
    public static final String SEND_SIGNUP_EMAIL_PATH = "/sign-up/email";
    public static final String CHECK_SIGNUP_EMAIL_PATH = "/sign-up/email/check/{authCode}";
}
