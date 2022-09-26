package com.seollem.server.book.dto;

import com.seollem.server.book.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class BookDto {

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post{
        @NotBlank
        private String title;
        private String author;
        private String cover;
        private int itemPage;
        private int currentPage;
        private String publisher;
        @NotBlank
        private Book.BookStatus bookStatus;
        private LocalDateTime readStarDate;
        private LocalDateTime readEndDate;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch{
        private String author;
        private int itemPage;
        private int currentPage;
        private String publisher;
        private Book.BookStatus bookStatus;
        private LocalDateTime readStarDate;
        private LocalDateTime readEndDate;
        private int star;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostResponse{
        private long bookId;
        private String title;
        private String author;
        private String cover;
        private Book.BookStatus bookStatus;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PatchResponse{
        private String author;
        private String publisher;
        private int itemPage;
        private LocalDateTime readStartDate;
        private LocalDateTime readEndDate;
        private String bookStatus;
        private int star;
        private int currentPage;
    }

    @Setter
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
