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

        // Extract JWT from the request header
        String token = jwtTokenProvider.resolveToken(request);

        // If no token, continue the filter chain
        if (token == null) {
            chain.doFilter(request, response);
            return;
        }

        // Check if the token is in the blacklist
        if (tokenBlacklist.contains(token)) {
            logger.warning("Token is in blacklist: " + token);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Blacklisted token.");
            return;
        }

        // Validate the token
        if (!jwtTokenProvider.validateToken(token)) {
            logger.warning("Invalid token: " + token);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid token.");
            return;
        }

        // If valid, set authentication in SecurityContext
        UsernamePasswordAuthenticationToken authentication = jwtTokenProvider.getAuthentication(token);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Continue the filter chain
        chain.doFilter(request, response);
    }
}
