package com.seollem.server.externallibrary.controller;

import com.seollem.server.externallibrary.AladdinResponseDto;
import com.seollem.server.externallibrary.service.AladdinService;
import com.seollem.server.externallibrary.util.BuildAladdinUriUtil;
import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ext-lib")
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class AladdinController {

  private final BuildAladdinUriUtil buildAladdinUriUtil;
  private final AladdinService aladdinService;

  @GetMapping("/{title-or-author}")
  public ResponseEntity getSearchedBook(@PathVariable("title-or-author") String input)
      throws Exception {

    URI uri = buildAladdinUriUtil.buildSearchUri(input);

    AladdinResponseDto response = aladdinService.getResponse(uri);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("best-seller")
  public ResponseEntity getBestSeller() throws Exception {

    URI uri = buildAladdinUriUtil.buildRecommendUri("bestseller");

    AladdinResponseDto response = aladdinService.getResponse(uri);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }


  @GetMapping("item-new-special")
  public ResponseEntity getItemNewSpecial() throws Exception {

    URI uri = buildAladdinUriUtil.buildRecommendUri("ItemNewSpecial");

    AladdinResponseDto response = aladdinService.getResponse(uri);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
