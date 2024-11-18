package org.kdt.mooluck.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtHandshakeInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        // JWT 토큰 가져오기
        String token = request.getURI().getQuery();
        if (token != null && token.startsWith("token=")) {
            token = token.substring(6); // "token=" 제거
        }

        // JWT 검증
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return false; // 검증 실패 시 WebSocket 연결 차단
        }

        // 검증된 사용자 정보 WebSocket 세션에 저장
        attributes.put("user", jwtTokenProvider.getAuthentication(token));
        return true; // 검증 성공 시 연결 허용
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception ex) {
        // 핸드셰이크 이후의 처리 (필요 시)
    }
}
