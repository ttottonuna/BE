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

@Component
public class JwtTokenProvider {

    // SecretKey for signing the JWT
    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Token expiration time (1 day)
    private final long EXPIRATION_TIME = 86400000;

    /**
     * Generate a JWT token with the given email as the subject.
     *
     * @param email The email to include in the token's subject.
     * @return The generated JWT token.
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Resolve the JWT token from the request header.
     *
     * @param request The HTTP request containing the token in the Authorization header.
     * @return The extracted token, or null if not present.
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Validate the provided JWT token.
     *
     * @param token The JWT token to validate.
     * @return true if the token is valid, false otherwise.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.err.println("Token expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("Unsupported token: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("Malformed token: " + e.getMessage());
        } catch (SignatureException e) {
            System.err.println("Invalid signature: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal argument token: " + e.getMessage());
        }
        return false;
    }

    /**
     * Extract the email (subject) from the JWT token.
     *
     * @param token The JWT token.
     * @return The email extracted from the token.
     */
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Generate an Authentication object for the user based on the JWT token.
     *
     * @param token The JWT token.
     * @return A UsernamePasswordAuthenticationToken containing the user's email.
     */
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String email = getEmailFromToken(token);
        User userDetails = new User(email, "", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
