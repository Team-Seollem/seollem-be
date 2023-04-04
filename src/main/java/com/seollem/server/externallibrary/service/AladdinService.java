package com.seollem.server.externallibrary.service;

import com.seollem.server.externallibrary.config.RestTemplateConfig;
import com.seollem.server.externallibrary.util.BuildAladdinUriUtil;
import java.net.URI;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AladdinService {

  private final RestTemplateConfig restTemplateConfig;
  private final BuildAladdinUriUtil buildAladdinUriUtil;

  public String getResponse(URI uri) throws Exception {
    RestTemplate restTemplate = restTemplateConfig.restTemplate();
    String rawBestSellerDataStr = restTemplate.getForObject(uri, String.class);

    // JSONObject 타입으로 편리하게 처리하기 위해 변환.
    JSONObject rawBestSellerDataJson = new JSONObject(rawBestSellerDataStr);

    JSONArray itemArr = rawBestSellerDataJson.getJSONArray("item");

    ArrayList<JSONObject> booksArr = new ArrayList<>();
    for (int i = 0; i < itemArr.length(); i++) {
      booksArr.add(itemArr.getJSONObject(i));
    }

    JSONArray responseBody = new JSONArray();
    JSONObject itemPageJson;
    // 0,2,4,13,16
    for (JSONObject object : booksArr) {
      JSONObject temp = new JSONObject();
      temp.put("title", object.getString("title"));
      temp.put("author", object.getString("author"));
      temp.put("cover", object.getString("cover"));
      temp.put("publisher", object.getString("publisher"));
      itemPageJson = findItemPageJson(object.getString("isbn"));
      temp.put("itemPage", itemPageJson.getString("itemPage"));
      responseBody.put(temp);
    }
    return responseBody.toString();
  }

  public JSONObject findItemPageJson(String isbn) throws JSONException {
    URI uri = buildAladdinUriUtil.buildItemPageUri(isbn);
    RestTemplate restTemplate = restTemplateConfig.restTemplate();
    String rawData = restTemplate.getForObject(uri, String.class);

    JSONObject rawDataJson = new JSONObject(rawData);
    JSONArray itemArr = rawDataJson.getJSONArray("item");

    JSONObject result = new JSONObject();
    result.put(
        "itemPage",
        itemArr.getJSONObject(0).getJSONObject("subInfo").getString("itemPage"));

    return result;
  }
}
