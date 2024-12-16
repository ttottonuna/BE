package org.kdt.mooluck.domain.elder.controller;

import org.kdt.mooluck.custom.CustomResponse;
import org.kdt.mooluck.domain.elder.service.ElderService;
import org.kdt.mooluck.domain.elder.dto.ElderDTO;
import org.kdt.mooluck.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/elders")
@CrossOrigin(origins = "http://localhost:5173")
public class ElderController {

    private final ElderService elderService;
    private final JwtTokenProvider jwtTokenProvider;

    public ElderController(ElderService elderService, JwtTokenProvider jwtTokenProvider) {
        this.elderService = elderService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Elder 로그인 엔드포인트: Access Token과 Refresh Token 발급
     */
    @PostMapping("/login")
    public ResponseEntity<CustomResponse> login(@RequestBody ElderDTO elderDTO) {
        // Validate the user's credentials
        boolean isValid = elderService.validateMember(elderDTO);
        if (!isValid) {
            // 로그인 실패 메시지 반환
            return ResponseEntity.ok(CustomResponse.message("로그인 실패: 이메일 또는 비밀번호를 확인해주세요."));
        }

        // Generate Access Token and Refresh Token
        String accessToken = jwtTokenProvider.generateAccessToken(elderDTO.getElderAccount());
        String refreshToken = jwtTokenProvider.generateRefreshToken(elderDTO.getElderAccount());

        // Return the tokens in the desired response format
        return ResponseEntity.ok(CustomResponse.success(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        )));
    }

    /**
     * Access Token 재발급 엔드포인트: Refresh Token 검증 후 새 Access Token 발급
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<CustomResponse> refreshAccessToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        // Refresh Token 검증
        if (refreshToken == null || !jwtTokenProvider.validateToken(refreshToken, true)) {
            return ResponseEntity.ok(CustomResponse.message("재발급 실패: 유효하지 않은 Refresh Token입니다."));
        }

        // Refresh Token에서 사용자 계정 추출
        String elderAccount = jwtTokenProvider.getEmailFromToken(refreshToken, true);

        // 새 Access Token 생성
        String newAccessToken = jwtTokenProvider.generateAccessToken(elderAccount);

        // 새 Access Token 반환
        return ResponseEntity.ok(CustomResponse.success(Map.of("accessToken", newAccessToken)));
    }
}
