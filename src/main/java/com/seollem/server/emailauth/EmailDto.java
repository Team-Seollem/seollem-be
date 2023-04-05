package com.seollem.server.emailauth;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class EmailDto {

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class JoinAuthCodeReq {

    @Email(message = "회원 이메일 형식이 아닙니다")
    @NotBlank(message = "회원 이메일이 입력되어야 합니다.")
    private String joinAuthCodeEmail;
  }
}
