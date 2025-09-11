package com.example.internetprograming_backend.service;

import com.example.internetprograming_backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public void memberSignUp() {

    }
}
