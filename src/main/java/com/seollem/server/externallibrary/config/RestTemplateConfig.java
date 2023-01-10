package com.seollem.server.externallibrary.config;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  @Bean
  public RestTemplate restTemplate() {
    HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory =
        new HttpComponentsClientHttpRequestFactory();
    httpComponentsClientHttpRequestFactory.setReadTimeout(3000); // read Timeout millisec
    httpComponentsClientHttpRequestFactory.setConnectTimeout(
        3000); // connection Timeout millisec
    CloseableHttpClient httpClient =
        HttpClientBuilder.create().setMaxConnTotal(500).setMaxConnPerRoute(100).build();
    httpComponentsClientHttpRequestFactory.setHttpClient(httpClient);

    RestTemplate restTemplate =
        new RestTemplate(
            new BufferingClientHttpRequestFactory(
                httpComponentsClientHttpRequestFactory));

    // Interceopter 설정
    List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
    if (CollectionUtils.isEmpty(interceptors)) {
      interceptors = new ArrayList<>();
    }
    interceptors.add(new RestTemplateInterceptor());
    restTemplate.setInterceptors(interceptors);

    return restTemplate;
  }
}
