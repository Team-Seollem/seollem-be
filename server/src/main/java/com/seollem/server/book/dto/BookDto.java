package com.seollem.server.book.dto;

import com.seollem.server.book.entity.Book;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class BookDto {

    @Getter
    public static class Post{
        @NotBlank
        private String title;
        private String author;
        private String Cover;
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
    public static class PostResponse{
        private String title;
        private String author;
        private String cover;
        private Book.BookStatus bookStatus;
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
