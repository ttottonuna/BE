package org.kdt.mooluck.domain.alert.controller;

import org.kdt.mooluck.domain.alert.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ButtonAlertController {

    @Autowired
    private AlertService alertService;

    // 버튼 클릭 시 기록 및 알림 해결
    @PostMapping("/button-click")
    public ResponseEntity<String> recordButtonClick(@RequestParam int elderId) {
        alertService.recordClick(elderId);
        return ResponseEntity.ok("Button click recorded and alert resolved");
    }
}