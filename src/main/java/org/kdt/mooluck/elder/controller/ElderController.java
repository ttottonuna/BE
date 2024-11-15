package org.kdt.mooluck.elder.controller;

import org.kdt.mooluck.custom.CustomResponse;
import org.kdt.mooluck.elder.dto.ElderDTO;
import org.kdt.mooluck.elder.service.ElderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/api/elders")
public class ElderController {

    private final ElderService elderService;

    @Autowired
    public ElderController(ElderService elderService) {
        this.elderService = elderService;
    }

    @PostMapping("/login")
    public ResponseEntity<CustomResponse> login(@RequestBody ElderDTO elderDto) {
        boolean isValid = elderService.validateMember(elderDto);
        if (isValid) {
            return ResponseEntity.ok(CustomResponse.success("Login successful"));
        } else {
            return ResponseEntity.status(401).body(CustomResponse.message("Invalid credentials"));
        }
    }


//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody ElderDTO elderDto) {
//        boolean isValid = elderService.validateMember(elderDto);
//        if (isValid) {
//            return ResponseEntity.ok("Login successful");
//        } else {
//            return ResponseEntity.status(401).body("Invalid credentials");
//        }
//    }
}
