package org.kdt.mooluck.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.getName());

    private final JwtTokenProvider jwtTokenProvider;
    private final Set<String> tokenBlacklist;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, Set<String> tokenBlacklist) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenBlacklist = tokenBlacklist;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String token = jwtTokenProvider.resolveToken(request);

        if (token == null) {
            logger.info("No token found in request.");
            chain.doFilter(request, response);
            return;
        }

        if (tokenBlacklist.contains(token)) {
            logger.warning("Token is in blacklist: " + token);
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Blacklisted token.");
            return;
        }

        if (!jwtTokenProvider.validateToken(token, false)) { // Add `false` for AccessToken validation
            logger.warning("Invalid token: " + token);
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid token.");
            return;
        }

        // 토큰이 유효한 경우 인증 정보 설정
        UsernamePasswordAuthenticationToken authentication = jwtTokenProvider.getAuthentication(token);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        logger.info("Token validated successfully for request: " + request.getRequestURI());

        chain.doFilter(request, response);
    }

    /**
     * 에러 응답을 작성하는 유틸리티 메서드
     */
    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.getWriter().write("{ \"error\": \"" + message + "\" }");
    }
}

