package com.seollem.server.book.controller;

import com.seollem.server.util.GetEmailFromHeaderTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

    private final GetEmailFromHeaderTokenUtil getEmailFromHeaderTokenUtil;

    public BookController(GetEmailFromHeaderTokenUtil getEmailFromHeaderTokenUtil) {
        this.getEmailFromHeaderTokenUtil = getEmailFromHeaderTokenUtil;
    }

    @GetMapping("/library")
    public ResponseEntity getLibrary(@RequestHeader Map<String, Object> requestHeader){
        String email = getEmailFromHeaderTokenUtil.getEmailFromHeaderToken(requestHeader);

        return new ResponseEntity(HttpStatus.OK);
    }
}
