package com.seollem.server.book;

import com.seollem.server.globaldto.PageInfo;
import com.seollem.server.memo.MemoDto;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class BookDto {

  @Setter
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Post {

    @NotBlank
    private String title;
    private String author;
    private String cover;
    private int itemPage;
    private int currentPage;
    private String publisher;
    private Book.BookStatus bookStatus;
    private LocalDateTime readStartDate;
    private LocalDateTime readEndDate;
  }

  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Patch {

    private String author;
    private int itemPage;
    private int currentPage;
    private String publisher;
    private Book.BookStatus bookStatus;
    private LocalDateTime readStartDate;
    private LocalDateTime readEndDate;
    private int star;
  }

  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class PostResponse {

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
  public static class PatchResponse {

    private String author;
    private String publisher;
    private int itemPage;
    private LocalDateTime readStartDate;
    private LocalDateTime readEndDate;
    private Book.BookStatus bookStatus;
    private int star;
    private int currentPage;
  }

  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class DetailResponse {

    private long bookId;
    private String title;
    private String cover;
    private String author;
    private String publisher;
    private LocalDateTime createdAt;
    private int star;
    private int currentPage;
    private int itemPage;
    private Book.BookStatus bookStatus;
    private LocalDateTime readStartDate;
    private LocalDateTime readEndDate;
    private List<MemoDto.Response> memosList;
    private int memoCount;
  }

  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class MemosOfBook {

    private long bookId;
    private List<MemoDto.Response> memosList;
  }

  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class LibraryResponse {

    private long bookId;
    private String title;
    private String cover;
    private String author;
    private LocalDateTime createdAt;
    private int star;
    private int currentPage;
    private int itemPage;
    private Book.BookStatus bookStatus;
    private int memoCount;
  }

  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class CalenderResponse {

    private long bookId;
    private LocalDateTime readEndDate;
    private String cover;
  }

  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class AbandonResponse {

    private long bookId;
    private LocalDateTime createdAt;
    private String title;
    private String cover;
  }

  @Setter
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class BooksHaveMemoResponse {

    private long bookId;
    private String title;
    private String cover;
    private int memoCount;
  }


  @Setter
  @Getter
  @NoArgsConstructor
  public static class BooksHaveMemoResponseDto<T> {

    private List<T> item;
    private PageInfo pageInfo;

    public BooksHaveMemoResponseDto(List<T> item, PageInfo pageInfo) {
      this.item = item;
      this.pageInfo = pageInfo;
    }

  }
}
