package com.seollem.server.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Keys {

  @Value("${api-external-library-key}")
  private String externalLibraryKey;

  @Value("${jwt-secret-key}")
  private String jwtSecretKey;

  private Keys() {
  }

}
