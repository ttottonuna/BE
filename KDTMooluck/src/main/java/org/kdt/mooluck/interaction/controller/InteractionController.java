package org.kdt.mooluck.interaction.controller;

import lombok.RequiredArgsConstructor;
import org.kdt.mooluck.interaction.dto.InteractionDTO;
import org.kdt.mooluck.interaction.service.InteractionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value="/api/v1/interaction")
@RequiredArgsConstructor
public class InteractionController {

    private final InteractionService interactionService;

    @GetMapping("/{elderId}")
    public ResponseEntity<List<InteractionDTO>> getInteractionList(@PathVariable Integer elderId) {
        List<InteractionDTO> response = interactionService.getInteractionList(elderId);
        return ResponseEntity.ok(response);
    }

}
