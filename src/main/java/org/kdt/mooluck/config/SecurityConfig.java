package org.kdt.mooluck.config;

import org.kdt.mooluck.security.JwtAuthenticationFilter;
import org.kdt.mooluck.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.Set;

@Configuration
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final Set<String> tokenBlacklist;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider, Set<String> tokenBlacklist) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenBlacklist = tokenBlacklist;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 추가
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/admin/signup", "/admin/login", "/api/elders/login", "/api/elders/refresh-token",
                                "/api/weather", "/interaction/**", "/admin/table",
                                "/swagger-ui/**", "/swagger-ui.html", "/swagger-resources/**",
                                "/v3/api-docs/**", "/webjars/**", "/", "/error", "/auth/validate"
                        ).permitAll() // signup, login은 모두 허용
                        .anyRequest().authenticated() // 다른 요청은 인증 필요
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider, tokenBlacklist),
                        UsernamePasswordAuthenticationFilter.class // 이 필터 전에 추가
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // CORS 설정
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:8080")); // 허용할 Origin
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP Method
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With", "Accept", "Origin")); // 허용할 헤더
        config.setExposedHeaders(List.of("Authorization")); // 노출할 헤더

        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
