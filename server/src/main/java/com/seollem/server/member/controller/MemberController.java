package com.seollem.server.member.controller;


import com.seollem.server.member.dto.MemberDto;
import com.seollem.server.member.entity.Member;
import com.seollem.server.member.mapper.MemberMapper;
import com.seollem.server.member.service.MemberService;
import com.seollem.server.util.GetEmailFromHeaderTokenUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/members")
@Validated
public class MemberController {

    private final MemberMapper memberMapper;
    private final MemberService memberService;
    private final GetEmailFromHeaderTokenUtil getEmailFromHeaderTokenUtil;

    public MemberController(MemberMapper memberMapper, MemberService memberService, GetEmailFromHeaderTokenUtil getEmailFromHeaderTokenUtil) {
        this.memberMapper = memberMapper;
        this.memberService = memberService;
        this.getEmailFromHeaderTokenUtil = getEmailFromHeaderTokenUtil;
    }

    @GetMapping(path = "/me")
    public ResponseEntity getMember(@RequestHeader Map<String, Object> requestHeader) {
        String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
        Member member = memberService.findVerifiedMemberByEmail(email);


        return new ResponseEntity<>(memberMapper.memberToMemberGetResponse(member), HttpStatus.OK);
    }

    @PatchMapping("/me")
    public ResponseEntity patchMember(@RequestHeader Map<String, Object> requestHeader,
                                      @Valid @RequestBody MemberDto.Patch requestBody){
        String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);

        Member findMember = memberService.findVerifiedMemberByEmail(email);
        Member patchMember = memberMapper.memberPatchToMember(requestBody);

        patchMember.setEmail(findMember.getEmail());

        Member member = memberService.updateMember(patchMember);

        return new ResponseEntity<>(memberMapper.memberToMemberPatchResponse(member), HttpStatus.OK);
    }

    @DeleteMapping("/me")
    public ResponseEntity patchMember(@RequestHeader Map<String, Object> requestHeader) {
        String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);

        memberService.deleteMember(email);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
