package org.kdt.mooluck.domain.admin.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.kdt.mooluck.custom.CustomResponse;
import org.kdt.mooluck.domain.admin.dto.AgencyStaffDTO;
import org.kdt.mooluck.domain.admin.dto.AgencyTableDTO;
import org.kdt.mooluck.domain.admin.dto.ElderDTO;
import org.kdt.mooluck.domain.admin.service.AgencyStaffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Controller", description = "API about admin Service")

public class AgencyStaffController {

    private final AgencyStaffService service;

    public AgencyStaffController(AgencyStaffService service) {
        this.service = service;
    }

    @PostMapping("/signup")
    public ResponseEntity<CustomResponse> register(@RequestBody AgencyStaffDTO staff) {
        service.register(staff);
        return ResponseEntity.ok(CustomResponse.message("회원가입 성공"));
    }

    @PostMapping("/login")
    public ResponseEntity<CustomResponse> login(@RequestBody AgencyStaffDTO staff) {
        String token = service.login(staff.getStaff_email(), staff.getPassword());
        if (token != null) {
            return ResponseEntity.ok(CustomResponse.success(Map.of("token", token)));
        } else {
            return ResponseEntity.ok(CustomResponse.message("로그인 실패: 이메일 또는 비밀번호를 확인해주세요."));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<CustomResponse> logout(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 제거
        }
        service.logout(token); // 서비스 로직 호출
        return ResponseEntity.ok(CustomResponse.message("로그아웃 성공"));
    }

    @GetMapping("/table")
    public ResponseEntity<CustomResponse> getMembersByStaffId(@RequestParam Long staffId) {
        List<AgencyTableDTO> agencyTableDTOs = service.getMembersByStaffId(staffId);
        return ResponseEntity.ok(CustomResponse.success(agencyTableDTOs));
    }

    // elder 회원가입 (관리자 권한)
    @PostMapping("/elder/signup")
    public ResponseEntity<CustomResponse> registerElder(@RequestBody ElderDTO elder) {
        service.registerElder(elder);
        return ResponseEntity.ok(CustomResponse.message("노인 등록 성공"));
    }

    // elder 정보 수정 (관리자 권한)
    @PutMapping("/elder/update/{elderId}")
    public ResponseEntity<CustomResponse> updateElder(@PathVariable Long elderId, @RequestBody ElderDTO elder) {
        elder.setElderId(elderId);
        service.updateElder(elder);
        return ResponseEntity.ok(CustomResponse.message("노인 정보 수정 성공"));
    }

    // elder 삭제 (관리자 권한)
    @DeleteMapping("/elder/delete/{elderId}")
    public ResponseEntity<CustomResponse> deleteElder(@PathVariable Long elderId) {
        service.deleteElder(elderId);
        return ResponseEntity.ok(CustomResponse.message("노인 정보 삭제 성공"));
    }
}
