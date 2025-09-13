package com.example.internetprograming_backend.controller;

import com.example.internetprograming_backend.common.Path.AuthEndPoint;
import com.example.internetprograming_backend.common.exception.CustomExceptionResponse;
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

    @PostMapping(AuthEndPoint.SIGNUP_PATH)
    public ResponseEntity<?> SignUp(SignUpForm signUpForm) {

        authService.signUp(signUpForm);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomExceptionResponse.SUCCESS.getMessage());
    }

    @GetMapping(AuthEndPoint.SEND_SIGNUP_EMAIL_PATH)
    public ResponseEntity<?> sendSignUpEmail(String email) {

        authService.sendSignUpEmail(email);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomExceptionResponse.SEND_EMAIL_COMPLETE.getMessage());
    }

    @GetMapping(AuthEndPoint.CHECK_SIGNUP_EMAIL_PATH)
    public ResponseEntity<?> checkSignUpEmail(@PathVariable String authCode) {

        authService.checkSignUpEmail(authCode);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CustomExceptionResponse.CHECK_EMAIL_VERIFICATION.getMessage());
    }

}
