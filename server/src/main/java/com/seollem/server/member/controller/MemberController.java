package com.seollem.server.member.controller;

import com.seollem.server.member.entity.Member;
import com.seollem.server.member.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/members")
public class MemberController {


    @GetMapping("/user")
    public String user() {
        return "user";
    }


    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
