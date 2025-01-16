package org.kdt.mooluck.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

@Component
public class JwtTokenProvider {

    private static final Logger logger = Logger.getLogger(JwtTokenProvider.class.getName());

    private final SecretKey ACCESS_SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final SecretKey REFRESH_SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long ACCESS_TOKEN_EXPIRATION = 60000; // 1 min
    private final long REFRESH_TOKEN_EXPIRATION = 604800000; // 7 days

    /**
     * Access Token 생성
     */
    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(ACCESS_SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Refresh Token 생성
     */
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(REFRESH_SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // admin -> access Token 생성
    public String generateAdminAccessToken(String email, Integer staffId) {
        return Jwts.builder()
                .setSubject(email)
                .claim("staff_id", staffId) // staff_id를 claim에 추가
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(ACCESS_SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }


    /**
     * 요청 헤더에서 토큰 추출
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 제거
        }
        return null;
    }

    /**
     * 토큰 유효성 검증
     */
    public boolean validateToken(String token, boolean isRefreshToken) {
        try {
            SecretKey key = getSecretKey(isRefreshToken);
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.warning("Expired token: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.warning("Unsupported token: " + e.getMessage());
        } catch (MalformedJwtException e) {
            logger.warning("Malformed token: " + e.getMessage());
        } catch (SignatureException e) {
            logger.warning("Invalid signature: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.warning("Empty or null token: " + e.getMessage());
        }
        return false;
    }

    /**
     * 토큰에서 이메일 추출
     */
    public String getEmailFromToken(String token, boolean isRefreshToken) {
        try {
            SecretKey key = getSecretKey(isRefreshToken);
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        } catch (JwtException e) {
            logger.warning("Failed to extract email from token: " + e.getMessage());
            throw new IllegalArgumentException("Invalid token");
        }
    }

    /**
     * Access Token으로 인증 정보 생성
     */
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String email = getEmailFromToken(token, false); // Access Token만 사용
        User userDetails = new User(email, "", new ArrayList<>()); // 권한 없이 기본 사용자 생성
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Access 또는 Refresh Secret Key 반환
     */
    private SecretKey getSecretKey(boolean isRefreshToken) {
        return isRefreshToken ? REFRESH_SECRET_KEY : ACCESS_SECRET_KEY;
    }
}
