package com.seollem.server.temppassword;

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
    StringBuilder result = new StringBuilder();

    char[] pwCollectionSpCha = new char[] {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')'};
    char[] pwCollectionNum = new char[] {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0',};
    char[] pwCollectionEng = new char[] {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
        'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
        's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    return getRandPw(4, pwCollectionEng) + getRandPw(1,
        pwCollectionSpCha) + getRandPw(4, pwCollectionNum) + getRandPw(2,
        pwCollectionSpCha);
  }

  public String getRandPw(int size, char[] pwCollection) {
    String ranPw = "";

    for (int i = 0; i < size; i++) {
      int selectRandomPw = (int) (Math.random() * (pwCollection.length));
      ranPw += pwCollection[selectRandomPw];
    }
    return ranPw;
  }

}
