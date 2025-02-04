package org.kdt.mooluck.domain.auth.controller;

import org.kdt.mooluck.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(400).body("Missing or invalid Authorization header");
        }

        String token = authorizationHeader.substring(7); // "Bearer " 제거

        if (!jwtTokenProvider.validateToken(token, false)) { // Access Token 검증
            return ResponseEntity.status(401).body("Invalid token");
        }

        return ResponseEntity.ok("Token is valid");
    }
}
