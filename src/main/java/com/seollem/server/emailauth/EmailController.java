package com.seollem.server.emailauth;

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
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

  private final EmailService emailService;

  @PostMapping()
  public ResponseEntity sendEmail(@Valid @RequestBody EmailRequestDto requestedEmail) {
    emailService.send(requestedEmail.getRequestedEmail());

    return new ResponseEntity(HttpStatus.CREATED);
  }
}
