package com.example.internetprograming_backend.controller;

import com.example.internetprograming_backend.common.path.AuthEndPoint;
import com.example.internetprograming_backend.common.exception.CustomExceptionResponse;
import com.example.internetprograming_backend.data.dto.jwt.JwtToken;
import com.example.internetprograming_backend.data.form.SignInForm;
import com.example.internetprograming_backend.data.form.SignUpForm;
import com.example.internetprograming_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AuthEndPoint.BASE_AUTH_PATH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(AuthEndPoint.AUTH_SIGNUP_PATH)
    public ResponseEntity<?> SignUp(SignUpForm signUpForm) {

        authService.signUp(signUpForm);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomExceptionResponse.SUCCESS.getMessage());
    }

    @GetMapping(AuthEndPoint.AUTH_SEND_SIGNUP_EMAIL_PATH)
    public ResponseEntity<?> sendSignUpEmail(String email) {

        authService.sendSignUpEmail(email);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomExceptionResponse.SEND_EMAIL_COMPLETE.getMessage());
    }

    @GetMapping(AuthEndPoint.AUTH_CHECK_SIGNUP_EMAIL_PATH)
    public ResponseEntity<?> checkSignUpEmail(@PathVariable String authCode) {

        authService.checkSignUpEmail(authCode);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomExceptionResponse.CHECK_EMAIL_VERIFICATION.getMessage());
    }

    @PostMapping(AuthEndPoint.AUTH_SIGN_IN_PATH)
    public ResponseEntity<?> signIn(SignInForm signInForm) {

        JwtToken jwtToken = authService.signIn(signInForm);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization","Bearer " + jwtToken.getAccessToken())
                .header("Refresh-Token","Bearer " + jwtToken.getRefreshToken())
                .body(CustomExceptionResponse.SUCCESS.getMessage());
    }

}
