package com.example.internetprograming_backend.data.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Builder @Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken {

    private String accessToken;

    private String refreshToken;

}
