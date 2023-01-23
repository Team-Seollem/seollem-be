package com.seollem.server.email;

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

  public void send(String email) {

    Random random = new Random();
    String authenticationCode = String.valueOf(random.nextInt(888888) + 111111); // 111111~999999
    String subject = "나만의 설렘 이메일 인증번호 입니다.";
    sender.setHost("smtp.gmail.com");

    try {
      MimeMessage message = sender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message);
      helper.setTo(email);
      helper.setSubject(subject);
      helper.setText("인증번호는 " + authenticationCode + " 입니다.");
      sender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }

    // redis에 인증번호 유효기간은 5분
    redisUtil.setDataExpire(email, authenticationCode, 60 * 5L);

  }
}
