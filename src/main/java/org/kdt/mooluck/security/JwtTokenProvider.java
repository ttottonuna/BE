package org.kdt.mooluck.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey ACCESS_SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final SecretKey REFRESH_SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long ACCESS_TOKEN_EXPIRATION = 3600000; // 1 hour
    private final long REFRESH_TOKEN_EXPIRATION = 604800000; // 7 days

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(ACCESS_SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(REFRESH_SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token, boolean isRefreshToken) {
        try {
            SecretKey key = isRefreshToken ? REFRESH_SECRET_KEY : ACCESS_SECRET_KEY;
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String getEmailFromToken(String token, boolean isRefreshToken) {
        SecretKey key = isRefreshToken ? REFRESH_SECRET_KEY : ACCESS_SECRET_KEY;
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String email = getEmailFromToken(token, false);
        User userDetails = new User(email, "", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
