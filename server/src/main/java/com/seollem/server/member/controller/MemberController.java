package com.seollem.server.member.controller;

import com.seollem.server.dto.SingleResponseDto;
import com.seollem.server.jwt.decoder.TokenDecodeService;
import com.seollem.server.member.dto.MemberDto;
import com.seollem.server.member.entity.Member;
import com.seollem.server.member.mapper.MemberMapper;
import com.seollem.server.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;


@RestController
@RequestMapping("/members")
@Validated
public class MemberController {

    private final MemberMapper memberMapper;
    private final MemberService memberService;
    private final TokenDecodeService tokenDecodeService;

    public MemberController(MemberMapper memberMapper, MemberService memberService, TokenDecodeService tokenDecodeService) {
        this.memberMapper = memberMapper;
        this.memberService = memberService;
        this.tokenDecodeService = tokenDecodeService;
    }

    @GetMapping(path = "/me")
    public ResponseEntity getMember(@RequestHeader Map<String, Object> requestHeader) {
        String token = requestHeader.get("authorization").toString(); // 헤더의 모든 값은 소문자로 받아진다.
        System.out.println(token);
        String email = tokenDecodeService.findEmail(token);
        Member member = memberService.findMemberByEmail(email);

        return new ResponseEntity<>(
                new SingleResponseDto<>(memberMapper.memberToMemberResponse(member)), HttpStatus.OK);
    }

}
