package org.kdt.mooluck.domain.alert.controller;

import org.kdt.mooluck.custom.CustomResponse;
import org.kdt.mooluck.domain.alert.dto.AlertDTO;
import org.kdt.mooluck.domain.alert.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    private final AlertService alertService;

    @Autowired
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    // 특정 elderId에 대한 모든 알림 조회
    @GetMapping("/{elderId}")
    public ResponseEntity<CustomResponse> getAlertsByElderId(@PathVariable int elderId) {
        List<AlertDTO> alerts = alertService.getAlertsByElderId(elderId);
        return ResponseEntity.ok(CustomResponse.success(alerts));
    }

    // 새로운 알림 생성
    @PostMapping("/create")
    public ResponseEntity<CustomResponse> createAlert(@RequestParam int elderId, @RequestParam String alertType) {
        alertService.createAlert(elderId, alertType);
        return ResponseEntity.ok(CustomResponse.message("알림이 생성되었습니다."));
    }

    // 특정 alertId에 대한 알림 해결
    @PostMapping("/{alertId}/resolve")
    public ResponseEntity<CustomResponse> resolveAlert(@PathVariable int alertId) {
        alertService.resolveAlert(alertId);
        return ResponseEntity.ok(CustomResponse.message("알림이 해결되었습니다."));
    }
}
