package org.kdt.mooluck.domain.interaction.controller;


import org.kdt.mooluck.domain.interaction.service.InteractionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/interaction")
public class InteractionController {

    private final InteractionService interactionService;

    public InteractionController(InteractionService interactionService) {
        this.interactionService = interactionService;

    }

    // 무럭이를 쓰다듬었을 때 pet_count 증가 API
    @PostMapping("/pet")
    public ResponseEntity<String> incrementPetCount(@RequestBody ElderRequest request) {
        Integer elderId = request.getElderId();
        if (elderId == null) {
            return ResponseEntity.badRequest().body("elderId가 제공되지 않았습니다.");
        }
        interactionService.incrementPetCount(elderId);
        return ResponseEntity.ok("무럭이를 쓰다듬어주었어요!");
    }

    // 물 주기를 했을 때 water_count 증가 API
    @PostMapping("/water")
    public ResponseEntity<String> incrementWaterCount(@RequestBody ElderRequest request) {
        Integer elderId = request.getElderId();
        System.out.println("반응해봐===============================================================: " + elderId); // 로그 추가
        if (elderId == null) {
            return ResponseEntity.badRequest().body("elderId가 제공되지 않았습니다.");
        }
        interactionService.incrementWaterCount(elderId);
        return ResponseEntity.ok("무럭이에게 물을 주었어요!");
    }
}
