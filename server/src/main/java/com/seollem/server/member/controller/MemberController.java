package com.seollem.server.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/members")
public class MemberController {
    @GetMapping
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello");
    }
}
