package com.seollem.server.email;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailRequestDto {

  @Email(message = "이메일 형식이어야 합니다")
  @NotBlank(message = "공백은 허용되지 않습니다")
  private String requestedEmail;
}
