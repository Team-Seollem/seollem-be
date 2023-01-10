package com.seollem.server.jwt.filter;

import com.seollem.server.jwt.decoder.TokenDecodeService;
import com.seollem.server.jwt.oauth.PrincipalDetails;
import com.seollem.server.member.Member;
import com.seollem.server.member.MemberService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private final MemberService memberService;
  private final TokenDecodeService tokenDecodeService;

  public JwtAuthorizationFilter(
      AuthenticationManager authenticationManager,
      MemberService memberService,
      TokenDecodeService tokenDecodeService) {
    super(authenticationManager);
    this.memberService = memberService;
    this.tokenDecodeService = tokenDecodeService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    //        System.out.println("인증이나 권한이 필요한 주소 요청 됨.");
    String jwtHeader = request.getHeader("Authorization");

    if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
      chain.doFilter(request, response);
      return;
    }
    String email = tokenDecodeService.findEmail(jwtHeader);

    if (email != null) {
      Member memberEntity = memberService.findVerifiedMemberByEmail(email);

      PrincipalDetails principalDetails = new PrincipalDetails(memberEntity);
      Authentication authentication =
          new UsernamePasswordAuthenticationToken(
              principalDetails, null, principalDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);

      chain.doFilter(request, response);
    }
    //        super.doFilterInternal(request, response, chain);
  }
}
