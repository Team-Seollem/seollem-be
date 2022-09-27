package com.seollem.server.externallibrary.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

// RestTemplate 로깅
@Slf4j
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
    private int count=0;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] reqBody, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request,reqBody);

        ClientHttpResponse response = execution.execute(request, reqBody);

        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException {
        count++;
        log.info(
                "===========================request begin================================================");
        log.info("URI         : {}", request.getURI());
        log.info("Method      : {}", request.getMethod());
        log.info("==========================request end==================================================");
        System.out.println("Count : " + count);
    }

}