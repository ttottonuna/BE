package org.kdt.mooluck.swaggerSample.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Elder Login Controller", description = "노인 로그인 관련 API입니다.")
public class ElderLoginController {

    @Operation(summary = "노인 로그인 엔드포인트", description = "사용자의 이메일과 비밀번호로 로그인합니다.")
    @GetMapping("/login")
    public String login(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password) {

        // Sample response
        if (email.equals("test@example.com") && password.equals("password123")) {
            return "로그인 성공!";
        } else {
            return "로그인 실패. 이메일 또는 비밀번호를 확인하세요.";
        }
    }
}
