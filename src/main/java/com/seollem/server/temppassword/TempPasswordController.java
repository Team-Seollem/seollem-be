package com.seollem.server.temppassword;

import com.seollem.server.member.Member;
import com.seollem.server.member.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email/password-change")
@RequiredArgsConstructor
public class TempPasswordController {

  private final TempPasswordService tempPasswordService;
  private final MemberService memberService;

  @PostMapping()
  public ResponseEntity sendTempPassword(@Valid @RequestBody TempPasswordDto tempPasswordDto) {

    String tempPwEmail = tempPasswordDto.getTempPasswordEmail();
    String tempPw = tempPasswordService.createTempPassword();
    Member tempPwMember = memberService.findVerifiedMemberByEmail(tempPwEmail);
    tempPwMember.setPassword(tempPw);
    memberService.updateMember(tempPwMember);
    tempPasswordService.send(tempPasswordDto.getTempPasswordEmail(), tempPw);

    return new ResponseEntity(HttpStatus.CREATED);
  }
}
