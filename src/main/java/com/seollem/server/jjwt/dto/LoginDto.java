package com.seollem.server.jjwt.dto;

import lombok.Getter;

@Getter
public class LoginDto {

        private String email;
        private String password;
    //클라이언트의 인증 정보를 수신할 LoginDto
}
