package com.seollem.server.externallibrary.controller;

import com.seollem.server.externallibrary.config.RestTemplateConfig;
import com.seollem.server.util.ApiKey;
import java.net.URI;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/ext-lib")
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class ExternalLibraryController {

  private final RestTemplateConfig restTemplateConfig;
  private final ApiKey apiKey;

  @GetMapping("best-seller")
  public ResponseEntity getBestSeller() throws JSONException {
    RestTemplate restTemplate = restTemplateConfig.restTemplate();

    UriComponents uriComponents =
        UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("www.aladin.co.kr")
            .path("/ttb/api/ItemList.aspx")
            .queryParam("ttbkey", apiKey.getKey())
            .queryParam("QueryType", "Bestseller")
            .queryParam("SearchTarget", "Book")
            .queryParam("output", "JS")
            .queryParam("MaxResults", "10")
            .queryParam("Cover", "Big")
            .queryParam("Version", "20131101")
            .encode()
            .build();

    URI uri = uriComponents.toUri();
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

    return new ResponseEntity(responseBody.toString(), HttpStatus.OK);
  }

  @GetMapping("/{title-or-author}")
  public ResponseEntity getSearchedBook(@PathVariable("title-or-author") String input)
      throws JSONException {
    RestTemplate restTemplate = restTemplateConfig.restTemplate();

    UriComponents uriComponents =
        UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("www.aladin.co.kr")
            .path("/ttb/api/ItemSearch.aspx")
            .queryParam("ttbkey", apiKey.getKey())
            .queryParam("Query", input)
            .queryParam("QueryType", "Keyword")
            .queryParam("SearchTarget", "Book")
            .queryParam("output", "JS")
            .queryParam("MaxResults", "10")
            .queryParam("Cover", "Big")
            .queryParam("Version", "20131101")
            .encode()
            .build();

    URI uri = uriComponents.toUri();
    String rawSearchedBookStr = restTemplate.getForObject(uri, String.class);

    // JSONObject 타입으로 편리하게 처리하기 위해 변환.
    JSONObject rawSearchedBookJson = new JSONObject(rawSearchedBookStr);

    JSONArray itemArr = rawSearchedBookJson.getJSONArray("item");

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

    return new ResponseEntity(responseBody.toString(), HttpStatus.OK);
  }

  @GetMapping("item-new-special")
  public ResponseEntity getItemNewSpecial() throws JSONException {
    RestTemplate restTemplate = restTemplateConfig.restTemplate();

    UriComponents uriComponents =
        UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("www.aladin.co.kr")
            .path("/ttb/api/ItemList.aspx")
            .queryParam("ttbkey", apiKey.getKey())
            .queryParam("QueryType", "ItemNewSpecial")
            .queryParam("SearchTarget", "Book")
            .queryParam("output", "JS")
            .queryParam("MaxResults", "10")
            .queryParam("Cover", "Big")
            .queryParam("Version", "20131101")
            .encode()
            .build();

    URI uri = uriComponents.toUri();
    String rawDataStr = restTemplate.getForObject(uri, String.class);

    // JSONObject 타입으로 편리하게 처리하기 위해 변환.
    JSONObject rawDataJson = new JSONObject(rawDataStr);

    JSONArray itemArr = rawDataJson.getJSONArray("item");

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

    return new ResponseEntity(responseBody.toString(), HttpStatus.OK);
  }

  // 전체 쪽수(itemPage)를 JSONObject 로 반환하는 메서드
  public JSONObject findItemPageJson(String isbn) throws JSONException {
    RestTemplate restTemplate = restTemplateConfig.restTemplate();

    UriComponents uriComponents =
        UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("www.aladin.co.kr")
            .path("/ttb/api/ItemLookUp.aspx")
            .queryParam("ttbkey", apiKey.getKey())
            .queryParam("itemIdType", "ISBN")
            .queryParam("ItemId", isbn)
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
    result.put(
        "itemPage",
        itemArr.getJSONObject(0).getJSONObject("subInfo").getString("itemPage"));

    return result;
  }
}
