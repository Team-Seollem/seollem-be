package com.seollem.server.emailauth;

import java.util.Optional;
import java.util.Random;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSenderImpl sender;
  private final EmailRedisUtil redisUtil;

  // 인증번호 유효기간 5분
  private final Long DURATION_MINUTES = 5L;
  private final Long DURATION_TRNAS_TO_SECONDS = DURATION_MINUTES * 60;
  private final String subject = "나만의 설렘 이메일 인증번호 입니다.";


  public void send(String email) {

    // 새로운 인증번호가 생성이 요청되면 기존 인증번호는 삭제된다.
    if (Optional.ofNullable(redisUtil.getData(email)).isEmpty()) {
      redisUtil.deleteData(email);
    }

    Random random = new Random();
    String authenticationCode = String.valueOf(random.nextInt(888888) + 111111); // 111111~999999
    String stringDuration = String.valueOf(DURATION_MINUTES);
    sender.setHost("smtp.gmail.com");

    try {
      MimeMessage message = sender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message);
      helper.setTo(email);
      helper.setSubject(subject);
      helper.setText("인증번호는 " + authenticationCode + " 입니다. 인증번호는 " + stringDuration + "분간 유효합니다.");
      sender.send(message);

    } catch (MessagingException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }

    redisUtil.setDataExpire(email, authenticationCode, DURATION_TRNAS_TO_SECONDS);

  }
}
