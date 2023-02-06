package com.seollem.server.jjwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seollem.server.jjwt.dto.LoginDto;
import com.seollem.server.jjwt.service.TokenService;
import com.seollem.server.member.Member;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  private final TokenService tokenService;


  @SneakyThrows
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) {

    ObjectMapper objectMapper = new ObjectMapper();    // ObjectMapper인스턴스 생성 (Dto클래스로  역직렬화)
    LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class); //

    // Token 생성
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

    return authenticationManager.authenticate(
        authenticationToken);  // AuthenticationManager에게 전달하면서 인증 처리를 위임
  }


  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException, ServletException {
    Member member = (Member) authResult.getPrincipal();

    String accessToken = tokenService.delegateAccessToken(member);  // Access Token 생성
    String refreshToken = tokenService.delegateRefreshToken(member); // Refresh Token 생성

    response.setHeader("Authorization", "Bearer " + accessToken);
    response.setHeader("Refresh", refreshToken);

    this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);

  }
}
