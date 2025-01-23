package org.kdt.mooluck.domain.watermanagement.controller;

import org.kdt.mooluck.domain.watermanagement.service.WaterManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/water-management")
public class WaterManagementController {

    private final WaterManagementService waterManagementService;

    public WaterManagementController(WaterManagementService waterManagementService) {
        this.waterManagementService = waterManagementService;
    }

    @PostMapping("/increment")
    public ResponseEntity<String> incrementWaterCount(@RequestBody Map<String, Integer> requestBody) {
        Integer interaction_id = requestBody.get("interaction_id");
        if (interaction_id == null) {
            return ResponseEntity.badRequest().body("id 없음.");
        }
        waterManagementService.incrementWaterCount(interaction_id);
        return ResponseEntity.ok("증가: " + interaction_id);
    }
}
