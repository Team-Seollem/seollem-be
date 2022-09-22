package com.seollem.server.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


public class MemberDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Get{
        @NotBlank(message = "공백은 허용되지 않습니다.")
        @Email(message = "이메일 형식이어야 합니다.")
        private String email;
    }

    @Getter
    @AllArgsConstructor
    public static class Post{
        @NotBlank(message = "공백은 허용되지 않습니다.")
        @Email(message = "이메일 형식이어야 합니다.")
        private String email;

        @NotBlank(message = "공백은 허용되지 않습니다..")
        @Pattern(regexp = "^(?=.[A-Za-z])(?=.\\d)(?=.[$@$!%#?&])[A-Za-z\\d$@$!%*#?&]{6,}$")
        private String name;

        @NotBlank(message = "공백은 허용되지 않습니다.")
        private String password;
    }
    @Getter
    @AllArgsConstructor
    public static class Patch {

    }

    @Getter
    @AllArgsConstructor
    public static class Response{

        private String email;
        private String name;
    }
}
