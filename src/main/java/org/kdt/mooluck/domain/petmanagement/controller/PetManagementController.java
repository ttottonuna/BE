package org.kdt.mooluck.domain.petmanagement.controller;

import org.kdt.mooluck.domain.petmanagement.service.PetManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/pet-management")
public class PetManagementController {

    private final PetManagementService petManagementService;

    public PetManagementController(PetManagementService petManagementService) {
        this.petManagementService = petManagementService;
    }

    @PostMapping("/increment")
    public ResponseEntity<String> incrementPetCount(@RequestBody Map<String, Integer> requestBody) {
        Integer interactionId = requestBody.get("interaction_id");
        if (interactionId == null) {
            return ResponseEntity.badRequest().body("interactionId is missing.");
        }
        petManagementService.incrementPetCount(interactionId);
        return ResponseEntity.ok("Pet count updated for interaction ID: " + interactionId);
    }
}
