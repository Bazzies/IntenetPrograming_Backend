package com.example.internetprograming_backend.common.path;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthEndPoint implements EndPoint {

    //security
    public static final String AUTH_SECURITY_WILDCARD = "/auth/**";

    // Base
    public static final String BASE_AUTH_PATH = "/auth";

    // URI
    public static final String AUTH_SIGNUP_PATH = "/sign-up";
    public static final String AUTH_SIGN_IN_PATH = "/sign-in";
    public static final String AUTH_SEND_SIGNUP_EMAIL_PATH = "/sign-up/email";
    public static final String AUTH_CHECK_SIGNUP_EMAIL_PATH = "/sign-up/email/check/{authCode}";
}
