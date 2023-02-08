package com.seollem.server.util;

import com.seollem.server.jjwt.jwt.JwtTokenizer;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetEmailFromHeaderTokenUtil {

  private final JwtTokenizer jwtTokenizer;


  public String getEmailFromHeaderToken(Map<String, Object> requestHeader) {
    String jws = requestHeader.get("authorization").toString().replace("Bearer ", "");
    String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
    Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();
    String email = (String) claims.get("email");

    return email;
  }
}
