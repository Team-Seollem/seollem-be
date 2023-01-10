package com.seollem.server.member;

import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberDto {

  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Post {

    @NotBlank(message = "공백은 허용되지 않습니다.")
    @Email(message = "이메일 형식이어야 합니다.")
    private String email;

    @NotBlank(message = "공백은 허용되지 않습니다.")
    private String name;

    @NotBlank(message = "공백은 허용되지 않습니다.")
    @Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{6,}$",
        message = "비밀번호는 알파벳, 숫자, 특수문자 포함 6자 이상이어야 합니다.")
    private String password;
  }

  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Patch {

    private String name;

    // Client에서 아예 null 보내는 것은 허용하나, password 필드가 존재한다면 정규식에 맞춰야함.
    @Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{6,}$",
        message = "비밀번호는 알파벳, 숫자, 특수문자 포함 6자 이상이어야 합니다.")
    private String password;
  }

  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class GetResponse {

    private String email;
    private String name;
  }

  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class PatchResponse {

    private String email;
    private String name;
    private LocalDateTime updatedAt;
  }
}
