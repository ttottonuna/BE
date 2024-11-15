package org.kdt.mooluck.elder.controller;

import org.kdt.mooluck.custom.CustomResponse;
import org.kdt.mooluck.elder.dto.ElderDTO;
import org.kdt.mooluck.elder.service.ElderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.*;
import java.util.*;


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
    public ResponseEntity<CustomResponse> getMemberByMemberId(@RequestBody ElderDTO elderDTO) {
        String elderId = elderService.getMemberByMemberId(elderDTO);

        // elderId 값을 Map으로 감싸서 전달
        Map<String, String> responseData = Map.of("elderId", elderId);
        CustomResponse response = CustomResponse.success(responseData);

        return ResponseEntity.ok(response);
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
