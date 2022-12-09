package com.seollem.server.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApiKey {

    @Value("${api-key}")
    private String key;

    private static ApiKey instance = new ApiKey();

    private ApiKey() {
    }

    public static ApiKey getInstance() {
        if (instance == null) {
            instance = new ApiKey();
        }
        return instance;
    }

}
