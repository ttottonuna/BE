package org.kdt.mooluck.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    private final long ACCESS_TOKEN_EXPIRATION = 60000;
    private final long REFRESH_TOKEN_EXPIRATION = 604800000;

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

    // admin -> access Token 생성
    public String generateAdminAccessToken(String email, Integer staffId) {
        return Jwts.builder()
                .setSubject(email)
                .claim("staff_id", staffId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(ACCESS_SECRET_KEY, SignatureAlgorithm.HS256)
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

    public String getEmailFromToken(String token, boolean isRefreshToken) {
        try {
            SecretKey key = getSecretKey(isRefreshToken);
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        } catch (JwtException e) {
            logger.warning("Failed to extract email from token: " + e.getMessage());
            throw new IllegalArgumentException("Invalid token");
        }
    }


    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String email = getEmailFromToken(token, false);
        User userDetails = new User(email, "", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private SecretKey getSecretKey(boolean isRefreshToken) {
        return isRefreshToken ? REFRESH_SECRET_KEY : ACCESS_SECRET_KEY;
    }
}
