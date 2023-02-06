package com.seollem.server.emailauth;

import com.seollem.server.member.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 인증 번호를 Email 로 보내는 역할만 한다.
 */
@RestController
@RequestMapping("/email/join")
@RequiredArgsConstructor
public class EmailController {

  private final EmailService emailService;
  private final MemberService memberService;

  @PostMapping()
  public ResponseEntity sendAuthCodeEmail(@Valid @RequestBody EmailRequestDto requestedEmail) {
    memberService.verifyExistsEmail(requestedEmail.getJoinAuthCodeEmail());
    emailService.send(requestedEmail.getJoinAuthCodeEmail());

    return new ResponseEntity(HttpStatus.CREATED);
  }
}
