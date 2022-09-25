package com.seollem.server.book.dto;

import lombok.Getter;

import java.time.LocalDateTime;

public class BookDto {

    @Getter
    public static class DetailResponse{
        private long bookId;
        private String title;
        private String cover;
        private String author;
        private String publisher;
        private LocalDateTime createdAt;
        private int star;
        private int currentPage;
        private int itemPage;
        private LocalDateTime readStartDate;
        private LocalDateTime readEndDate;
        //메모 구현 필요
    }
}
