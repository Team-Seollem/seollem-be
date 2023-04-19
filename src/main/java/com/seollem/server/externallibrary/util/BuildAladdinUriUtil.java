package com.seollem.server.externallibrary.util;

import com.seollem.server.util.Keys;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class BuildAladdinUriUtil {

  private final Keys keys;

  public URI buildSearchUri(String input) {
    UriComponents uriComponents =
        UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("www.aladin.co.kr")
            .path("/ttb/api/ItemSearch.aspx")
            .queryParam("ttbkey", keys.getExternalLibraryKey())
            .queryParam("Query", input)
            .queryParam("QueryType", "Keyword")
            .queryParam("SearchTarget", "Book")
            .queryParam("output", "JS")
            .queryParam("MaxResults", "10")
            .queryParam("Cover", "Big")
            .queryParam("Version", "20131101")
            .encode()
            .build();

    return uriComponents.toUri();
  }

  public URI buildRecommendUri(String queryType) {
    UriComponents uriComponents =
        UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("www.aladin.co.kr")
            .path("/ttb/api/ItemList.aspx")
            .queryParam("ttbkey", keys.getExternalLibraryKey())
            .queryParam("QueryType", queryType)
            .queryParam("SearchTarget", "Book")
            .queryParam("output", "JS")
            .queryParam("MaxResults", "10")
            .queryParam("Cover", "Big")
            .queryParam("Version", "20131101")
            .encode()
            .build();

    return uriComponents.toUri();
  }

  public URI buildItemPageUri(String isbn) {

    UriComponents uriComponents =
        UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("www.aladin.co.kr")
            .path("/ttb/api/ItemLookUp.aspx")
            .queryParam("ttbkey", keys.getExternalLibraryKey())
            .queryParam("itemIdType", "ISBN")
            .queryParam("ItemId", isbn)
            .queryParam("Cover", "Big")
            .queryParam("output", "JS")
            .queryParam("Version", "20131101")
            .encode()
            .build();
    return uriComponents.toUri();
  }

}
