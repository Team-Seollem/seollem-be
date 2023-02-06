package com.seollem.server.jjwt.service;

import com.seollem.server.emailauth.EmailRedisUtil;
import com.seollem.server.jjwt.jwt.JwtTokenizer;
import com.seollem.server.member.Member;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;


// 토큰을 생성하는 구체적인 로직
@Service
public class TokenService {

  private final Long DURATION = 60 * 5L;

  private final JwtTokenizer jwtTokenizer;

  private final EmailRedisUtil emailRedisUtil;

  public TokenService(JwtTokenizer jwtTokenizer,EmailRedisUtil emailRedisUtil) {
    this.jwtTokenizer = jwtTokenizer;
    this.emailRedisUtil = emailRedisUtil;

  }

  public String delegateAccessToken(Member member) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("username", member.getEmail());
    claims.put("roles", member.getRoles());

    String subject = member.getEmail();
    Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

    String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

    String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

    return accessToken;
  }


  public String delegateRefreshToken(Member member) {
    String subject = member.getEmail();
    Date expiration = jwtTokenizer.getRefreshTokenExpiration(jwtTokenizer.getRefreshTokenExpirationDays());
    String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

    String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

    emailRedisUtil.setDataExpireDay(member.getEmail(),refreshToken,DURATION);

    return refreshToken;
  }
}
