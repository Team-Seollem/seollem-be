package com.seollem.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class FindItemPageTest {
    @Test
    public void findItemPage() throws JSONException {
        RestTemplate restTemplate = new RestTemplate();

        UriComponents uriComponents =
                UriComponentsBuilder
                        .newInstance()
                        .scheme("http")
                        .host("www.aladin.co.kr")
                        .path("/ttb/api/ItemLookUp.aspx")
                        .queryParam("ttbkey", "ttbii123210947001")
                        .queryParam("itemIdType", "ISBN")
                        .queryParam("ItemId", "8936438832")
                        .queryParam("Cover", "Big")
                        .queryParam("output", "JS")
                        .queryParam("Version", "20131101")
                        .encode()
                        .build();
        URI uri = uriComponents.toUri();
        String rawData = restTemplate.getForObject(uri, String.class);

        JSONObject rawDataJson = new JSONObject(rawData);
        JSONArray itemArr = rawDataJson.getJSONArray("item");

        JSONObject result = new JSONObject();
        result.put("itemPage",itemArr.getJSONObject(0).getJSONObject("subInfo").getString("itemPage"));

        System.out.println(result);
//        return result;
    }
}
