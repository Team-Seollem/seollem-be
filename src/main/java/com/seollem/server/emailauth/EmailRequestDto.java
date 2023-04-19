package com.seollem.server.emailauth;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailRequestDto {

  @Email(message = "회원 이메일 형식이 아닙니다")
  @NotBlank(message = "회원 이메일이 입력되어야 합니다.")
  private String joinAuthCodeEmail;
}
