package com.seollem.server.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Keys {

  private static Keys instance = new Keys();

  @Value("${api-external-library-key}")
  private String externalLibraryKey;

  @Value("${jwt-secret-key}")
  private String jwtSecretKey;

  private Keys() {
  }

  public static Keys getInstance() {
    if (instance == null) {
      instance = new Keys();
    }
    return instance;
  }
}
