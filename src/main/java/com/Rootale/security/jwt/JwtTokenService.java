package com.Rootale.security.jwt;

import com.Rootale.member.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenService {
    @Value("${jwt.secret}") private String secret;
    @Value("${jwt.access-expiration}") private long accessExp;
    @Value("${jwt.refresh-expiration}") private long refreshExp;

    public String createAccessToken(User user) {
        Instant now = Instant.now();
        long expirationSeconds= accessExp/1000;

        return Jwts.builder()
                .subject(String.valueOf(user.getUsersId()))
                .claim("username", user.getUsername())
                .claim("email", user.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(accessExp)))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public String createRefreshToken(User user) {
        Instant now = Instant.now();
        long expirationSeconds= refreshExp/1000;

        return Jwts.builder()
                .subject(String.valueOf(user.getUsersId()))
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(refreshExp)))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public Long extractUserIdFromRefreshToken(String refreshToken) {
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload();

        String subject = claims.getSubject();
        return Long.parseLong(subject);
    }

    public Long extractUserIdFromAccessToken(String accessToken) {
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();

        String subject = claims.getSubject();
        return Long.parseLong(subject);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 토큰 유효성 검증
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 토큰에서 이메일 추출
     */
    public String extractEmail(String token) {
        Claims claims = extractClaims(token);
        return claims.get("email", String.class);
    }

    /**
     * 토큰에서 사용자명 추출
     */
    public String extractUsername(String token) {
        Claims claims = extractClaims(token);
        return claims.get("username", String.class);
    }

    /**
     * 토큰 만료 시간 확인
     */
    public Date getExpirationDate(String token) {
        Claims claims = extractClaims(token);
        return claims.getExpiration();
    }

    /**
     * 토큰이 만료되었는지 확인
     */
    public boolean isTokenExpired(String token) {
        try {
            Date expiration = getExpirationDate(token);
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}
