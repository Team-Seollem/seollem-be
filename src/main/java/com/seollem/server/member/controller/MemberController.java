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

import javax.validation.Valid;
import java.util.Map;


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
        System.out.println(email);
        if(email.equals("starrypro@gmail.com")) return new ResponseEntity<>("게스트용 아이디는 수정할 수 없어요", HttpStatus.BAD_REQUEST);

        else {
            Member findMember = memberService.findVerifiedMemberByEmail(email);
            Member patchMember = memberMapper.memberPatchToMember(requestBody);

            patchMember.setEmail(findMember.getEmail());

            Member member = memberService.updateMember(patchMember);

            return new ResponseEntity<>(memberMapper.memberToMemberPatchResponse(member), HttpStatus.OK);
        }
    }

    @DeleteMapping("/me")
    public ResponseEntity patchMember(@RequestHeader Map<String, Object> requestHeader) {
        String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);
        System.out.println(email);

        if(email.equals("starrypro@gmail.com")) return new ResponseEntity<>("게스트용 아이디는 삭제할 수 없어요", HttpStatus.BAD_REQUEST);


        else {
            memberService.deleteMember(email);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

}
