package com.seollem.server.temppassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TempPasswordDto {

  @NotBlank(message = "회원 이메일이 입력되어야 합니다.")
  @Email(message = "이메일 형식이 아닙니다")
  private String tempPasswordEmail;

}
