package com.seollem.server.jwt.decoder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

@Service
public class TokenDecodeService {

  public String findEmail(String token) {
    String jwtToken = token.replace("Bearer ", "");

    String email =
        JWT.require(
                Algorithm.HMAC512(
                    "d2VzZW9sbGVtdGVhbXNlcnZpY2VzdXNlcnNvd25saWJyYXJ5bWFuYWdlbWVudHdlaG9wZW91cnNlcnZpY2V0b2JldXNlZnVs"))
            .build()
            .verify(jwtToken)
            .getClaim("email")
            .asString();

    return email;
  }
}
