package com.seollem.server.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApiKey {

  private static ApiKey instance = new ApiKey();

  @Value("${api-key}")
  private String key;

  private ApiKey() {
  }

  public static ApiKey getInstance() {
    if (instance == null) {
      instance = new ApiKey();
    }
    return instance;
  }
}
