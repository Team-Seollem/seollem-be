//package com.seollem.server;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponents;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.net.URI;
//import java.util.ArrayList;
//
//@SpringBootTest
//class ServerApplicationTests {
//
//	@Test
//	void contextLoads() throws JSONException {
//		RestTemplate restTemplate = new RestTemplate();
//
//		UriComponents uriComponents =
//				UriComponentsBuilder
//						.newInstance()
//						.scheme("http")
//						.host("www.aladin.co.kr")
//						.path("/ttb/api/ItemList.aspx")
//						.queryParam("ttbkey", "ttbii123210947001")
//						.queryParam("QueryType", "Bestseller")
//						.queryParam("SearchTarget", "Book")
//						.queryParam("output", "JS")
//						.queryParam("Version", "20131101")
//						.encode()
//						.build();
//		URI uri = uriComponents.toUri();
//		String rawBestSellerDataStr = restTemplate.getForObject(uri, String.class);
//
//		// JSONObject 타입으로 편리하게 처리하기 위해 변환.
//		JSONObject rawBestSellerDataJson = new JSONObject(rawBestSellerDataStr);
//		/*
//			{
//				"item" :
//				[
//					{
//						title,
//						author,
//						cover,
//						publisher,
//						itemPage
//					},
//					{
//						title,
//						author,
//						cover,
//						publisher,
//						itemPage
//					},
//					...
//					...
//					...
//				]
//			}
//
//		 */
//		JSONArray itemArr = rawBestSellerDataJson.getJSONArray("item");
//
//		ArrayList<JSONObject> booksArr = new ArrayList<>();
//
//		for (int i=0;i<itemArr.length();i++){
//			booksArr.add(itemArr.getJSONObject(i));
//		}
//
//		JSONArray responseBody = new JSONArray();
//		JSONObject itemPageJson = new JSONObject();
//		// 0,2,4,13,16
//		for(JSONObject object : booksArr){
//			JSONObject temp = new JSONObject();
//			temp.put("title", object.getString("title"));
//			temp.put("author", object.getString("author"));
//			temp.put("cover", object.getString("cover"));
//			temp.put("publisher", object.getString("publisher"));
////			itemPageJson = findItemPage(object.getString("isbn"));
//			temp.put("isbn", itemPageJson.getString("itemPage"));
//			responseBody.put(responseBody);
//		}
//		System.out.println(responseBody);
//	}
//
//
//
//
//
//}
