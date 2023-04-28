package com.seollem.server.member.dto.othermemberbook;

import com.seollem.server.book.Book.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OtherMemberBookDto {

  private String title;
  private String author;
  private String cover;
  private String publisher;
  private int itemPage;
  private int currentPage;
  private int star;
  private BookStatus bookStatus;

}
