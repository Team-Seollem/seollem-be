package com.seollem.server.util;

import com.seollem.server.jjwt.decoder.TokenDecodeService;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class GetEmailFromHeaderTokenUtil {

  private final TokenDecodeService tokenDecodeService;

  public GetEmailFromHeaderTokenUtil(TokenDecodeService tokenDecodeService) {
    this.tokenDecodeService = tokenDecodeService;
  }

  public String getEmailFromHeaderToken(Map<String, Object> requestHeader) {
    String token = requestHeader.get("authorization").toString();
    String email = tokenDecodeService.findEmail(token);

    return email;
  }
}
