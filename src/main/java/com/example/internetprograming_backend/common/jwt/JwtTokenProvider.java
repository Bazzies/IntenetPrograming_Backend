package com.example.internetprograming_backend.common.jwt;

import com.example.internetprograming_backend.domain.Member;
import com.example.internetprograming_backend.domain.MemberRole;
import com.example.internetprograming_backend.repository.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final MemberRepository memberRepository;

    private final SecretKey secretKey;
    private final long accessTtlMinutes;
    private final long refreshTtlDays;

    private final static String MEMBER_ID = "memberId";
    private final static String ROLE = "role";


    public JwtTokenProvider(@Value("${jwt_secret_key}") String secretKey,
                            @Value("${jwt_access_token_ttl_minutes}") long accessTtlMinutes,
                            @Value("${jwt_refresh_token_ttl_days}") long refreshTtlDays,
                            MemberRepository memberRepository
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessTtlMinutes = accessTtlMinutes;
        this.refreshTtlDays = refreshTtlDays;
        this.memberRepository = memberRepository;
    }

    public String createAccessToken(Long memberId, Set<MemberRole> memberRoleSet) {
        return createJwtToken(memberId, memberRoleSet, accessTtlMinutes, true);
    }

    public String createRefreshToken(Long memberId, Set<MemberRole> memberRoleSet) {
        return createJwtToken(memberId, memberRoleSet, refreshTtlDays, false);
    }

    public String createJwtToken(Long memberId, Set<MemberRole> memberRoleSet, long expiredTime, boolean isAccessToken) {

        String authorities = memberRoleSet.stream()
                .map(memberRole -> memberRole.getTokenRole().getTokenRole())
                .collect(Collectors.joining(","));

        Claims claims;
        if (isAccessToken) {
            claims = Jwts.claims()
                    .add(JwtTokenProvider.MEMBER_ID, memberId)
                    .add(ROLE, authorities)
                    .build();
        } else {
            claims = Jwts.claims()
                    .add(JwtTokenProvider.MEMBER_ID, memberId)
                    .build();
        }

        return Jwts.builder()
                .claims(claims)
                .issuedAt(Date.from(Instant.now()))
                .expiration(
                        isAccessToken ? Date.from(Instant.now().plus(expiredTime, ChronoUnit.MINUTES))
                                : Date.from(Instant.now().plus(expiredTime, ChronoUnit.DAYS))
                )
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(resolveToken(token));
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    public Authentication getAuthorization(String resolveToken) {
        Claims claims = parseClaims(resolveToken);

        Long memberId = claims.get(MEMBER_ID, Long.class);
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("role", String.class).split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return new UsernamePasswordAuthenticationToken(member, "", authorities);
    }

    public String resolveToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7).trim();
        }
        return null;
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
