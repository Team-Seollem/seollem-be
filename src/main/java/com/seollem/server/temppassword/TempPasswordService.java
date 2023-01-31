package com.seollem.server.temppassword;

import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TempPasswordService {

  private final JavaMailSenderImpl sender;

  private final String subject = "나만의 설렘 임시 비밀번호 입니다.";

  public void send(String tempPasswordEmail, String tempPw) {

    sender.setHost("smtp.gmail.com");

    try {
      MimeMessage message = sender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message);
      helper.setTo(tempPasswordEmail);
      helper.setSubject(subject);
      helper.setText("임시 비밀번호는 " + tempPw + " 입니다. 기존 비밀번호로 로그인할 수 없습니다. 로그인 후 비밀번호를 변경해주세요.");
      sender.send(message);

    } catch (MessagingException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }

  }

  public String createTempPassword() {
    String tempPassword = UUID.randomUUID().toString().replace("-", ""); // '-'를 제거
    tempPassword = tempPassword.substring(0, 10); //임시 비밀번호는 10자리 문자열
    return tempPassword;
  }

}
