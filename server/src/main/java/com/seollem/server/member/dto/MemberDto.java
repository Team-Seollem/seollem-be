package com.seollem.server.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


public class MemberDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Get{
        @NotBlank(message = "Null이나 공백이 없어야 합니다.")
        @Email(message = "이메일 형식이어야 합니다.")
        private String email;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch{

    }

    @Getter
    @AllArgsConstructor
    public static class Response{

        private String email;
        private String name;
    }
}
