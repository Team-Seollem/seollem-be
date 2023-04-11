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

    @NotBlank(message = "회원 이메일이 입력되어야 합니다.")
    @Email(message = "회원 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "회원 이름이 입력되어야 합니다.")
    private String name;

    @NotBlank(message = "회원 비밀번호가 입력되어야 합니다.")
    @Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{6,}$",
        message = "회원 비밀번호는 알파벳, 숫자, 특수문자 포함 6자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "인증번호가 입력되어야 합니다.")
    private String authenticationCode;
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

    private String profile;

    private String url;
  }

  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class GetResponse {

    private String email;
    private String name;
    private String profile;
    private String url;
  }

  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class PatchResponse {

    private String email;
    private String name;
    private LocalDateTime updatedAt;
    private String profile;
    private String url;
  }

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ImageMemberResponse {

    private String url;
  }
}
