package com.seollem.server.member.controller;


import com.seollem.server.member.dto.MemberDto;
import com.seollem.server.member.entity.Member;
import com.seollem.server.member.mapper.MemberMapper;
import com.seollem.server.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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


    public MemberController(MemberMapper memberMapper, MemberService memberService) {
        this.memberMapper = memberMapper;
        this.memberService = memberService;
    }

    @GetMapping(path = "/me")
    public ResponseEntity getMember(Authentication authentication) {
        String email = authentication.getName();
        Member member = memberService.findVerifiedMemberByEmail(email);


        return new ResponseEntity<>(memberMapper.memberToMemberGetResponse(member), HttpStatus.OK);
    }

    @PatchMapping("/me")
    public ResponseEntity patchMember(Authentication authentication,
                                      @Valid @RequestBody MemberDto.Patch requestBody){
        String email = authentication.getName();

        Member findMember = memberService.findVerifiedMemberByEmail(email);
        Member patchMember = memberMapper.memberPatchToMember(requestBody);

        patchMember.setEmail(findMember.getEmail());

        Member member = memberService.updateMember(patchMember);

        return new ResponseEntity<>(memberMapper.memberToMemberPatchResponse(member), HttpStatus.OK);
    }

    @DeleteMapping("/me")
    public ResponseEntity patchMember(Authentication authentication) {
        String email = authentication.getName();

        memberService.deleteMember(email);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
