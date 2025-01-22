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

    @PostMapping("/login")
    public ResponseEntity<CustomResponse> login(@RequestBody ElderDTO elderDTO) {
        boolean isValid = elderService.validateMember(elderDTO);
        if (!isValid) {
            return ResponseEntity.ok(CustomResponse.message("로그인 실패: 이메일 또는 비밀번호를 확인해주세요."));
        }
        String accessToken = jwtTokenProvider.generateAccessToken(elderDTO.getElderAccount());
        String refreshToken = jwtTokenProvider.generateRefreshToken(elderDTO.getElderAccount());

        return ResponseEntity.ok(CustomResponse.success(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        )));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<CustomResponse> refreshAccessToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (refreshToken == null || !jwtTokenProvider.validateToken(refreshToken, true)) {
            return ResponseEntity.ok(CustomResponse.message("재발급 실패: 유효하지 않은 Refresh Token입니다."));
        }
        String elderAccount = jwtTokenProvider.getEmailFromToken(refreshToken, true);
        String newAccessToken = jwtTokenProvider.generateAccessToken(elderAccount);

        return ResponseEntity.ok(CustomResponse.success(Map.of("accessToken", newAccessToken)));
    }
}
