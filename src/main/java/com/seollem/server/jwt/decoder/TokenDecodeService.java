package com.seollem.server.jwt.decoder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.seollem.server.util.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenDecodeService {

  private final Keys keys;

  public String findEmail(String token) {
    String jwtToken = token.replace("Bearer ", "");

    String email =
        JWT.require(
                Algorithm.HMAC512(
                    keys.getJwtSecretKey()))
            .build()
            .verify(jwtToken)
            .getClaim("email")
            .asString();

    return email;
  }
}
